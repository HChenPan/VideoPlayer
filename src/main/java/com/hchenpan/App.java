package com.hchenpan;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import javax.swing.*;
import java.util.Objects;

/**
 * Hello world!
 *
 * @author Huangcp
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello");
        String path = Objects.requireNonNull(App.class.getClassLoader().getResource("vlc")).getPath();
        path = path.substring(1);
        /*加载VLC*/
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), path);
        System.out.println(LibVlc.INSTANCE.libvlc_get_version());
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), path + "\\sdk\\lib");
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
        JFrame frame = new JFrame("vlcj");
        final EmbeddedMediaPlayerComponent mediaPlayerComponent;
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent() {
            @Override
            protected String[] onGetMediaPlayerFactoryArgs() {

                return new String[]{"--video-title=vlcj video output", "--no-snapshot-preview", "--quiet-synchro",
                        "--sub-filter=logo:marq", "--intf=dummy", "--network-caching=1000", "--file-caching=10",
                        "--live-caching=10", "--clock-jitter=10",
                        "--tcp-caching=10",
                        "--h264-fps=5",
                        "--clock-synchro=1",
                        "--mosaic-delay=0",
                        "--sout-ts-shaping=0", "--sout-ts-dts-delay=0",
                        "--sout-ts-pcr=0", "--sout-ts-use-key-frames"
                };
            }
        };

        frame.setContentPane(mediaPlayerComponent);

        frame.setLocation(100, 100);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


        String mrl = "tcp://192.168.2.30:5000";
        mediaPlayerComponent.getMediaPlayer().playMedia(mrl, new String[]{":demux=h264"});


    }
}


