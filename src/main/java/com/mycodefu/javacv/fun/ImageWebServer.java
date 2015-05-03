package com.mycodefu.javacv.fun;

import com.mycodefu.javacv.fun.nanohttpd.NanoHTTPD;
import com.mycodefu.javacv.fun.nanohttpd.NanoHTTPD.Response.Status;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * NanoHTTPD web server which allows you to post images and run processes on them.
 *
 * Created by lthompson on 3/05/15.
 */
public class ImageWebServer extends NanoHTTPD {
    private static final Logger log = Logger.getLogger(ImageWebServer.class);

    public ImageWebServer(int port) {
        super("0.0.0.0", port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        final Response response;
        switch(session.getMethod()) {
            case GET:
            default: {
                response = new Response(Status.OK, MIME_HTML, this.getClass().getResourceAsStream("/index.html"));
                break;
            }
            case POST: {
                Map<String, String> files = new HashMap<>();
                try {
                    session.parseBody(files);
                } catch (ResponseException | IOException e) {
                    log.error("Error parsing the POST body.", e);
                }

                String extraData = session.getParms().get("extra_data");
                File file = new File(files.get("file"));

                System.out.println("Uploaded: " + file + "\n" + extraData);

                //todo: process the file, return the image, if failed redirect


                String uri = "/";
                response = new Response(Status.REDIRECT_TEMPORARY, NanoHTTPD.MIME_HTML, "<html><body>Redirected: <a href=\"" + uri + "\">" + uri + "</a></body></html>");
                response.addHeader("Location", uri);
                break;
            }
        }
        return response;
    }

    public static void run(int port) {
        ImageWebServer server = new ImageWebServer(port);
        try {
            server.start();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
            System.exit(-1);
        }

        System.out.println("Server started, Hit Enter to stop.\n");

        try {
            System.in.read();
        } catch (Throwable ignored) {
        }

        server.stop();
        System.out.println("Server stopped.\n");
    }
}
