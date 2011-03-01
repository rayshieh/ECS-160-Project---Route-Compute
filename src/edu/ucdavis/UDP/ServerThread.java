package edu.ucdavis.UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import android.content.Context;
import android.util.Log;
import edu.ucdavis.ecs160.Parameters;

public class ServerThread implements Runnable {
	private static final String TAG = "ServerThread";
	public final int SERVERPOT = 8080; //redir to
	public final String SERVERADDRESSNAME = "10.0.2.15";
	private Context context; //For Android pop-up messaging
	private Charset usedCS = Charset.forName(Parameters.CHARSET);
	private final CharsetDecoder decoder = usedCS.newDecoder();
	private final CharsetEncoder encoder = usedCS.newEncoder();
	
	public void setContext(Context appContext) {
		if( appContext != null )
			context = appContext;
	}
	
	public void run() {
		try {
			//Setup the socket to the right listening port, don't need the address
			//InetAddress ServerAddress = InetAddress.getByName(SERVERADDRESSNAME);
			DatagramSocket socket = new DatagramSocket(SERVERPOT);
			Log.v(TAG,"Socket Setuped");
			
			//Setup the DatagramPacket to receive and deliver package
			byte[] buf = new byte[1024];
			DatagramPacket packet = new DatagramPacket(buf, 1024);
			while(true) {
				Log.v(TAG,"Start listening...");
				socket.receive(packet);
				Log.v(TAG,"Packet Received!");
				
				//Decode the package
				ByteBuffer bbuf = ByteBuffer.allocate(1024);
				CharBuffer received = decoder.decode(bbuf.wrap(packet.getData()));
				Log.v(TAG, "Recived: " + received.toString());
				
				
				//Modify the content, get the address, and send it back
				bbuf = encoder.encode(CharBuffer.wrap(received.toString().toUpperCase()));
				byte[] sendData = bbuf.array();
				packet = new DatagramPacket(
						sendData, sendData.length, packet.getAddress(), packet.getPort());
				Log.v(TAG, "Start sending: " + sendData.toString());	
				socket.send(packet);
				Log.v(TAG, "Packet Sent!");
			}
		}catch(Exception e) {
			Log.v(TAG, e.toString());
		}
	}//run
}//class ClientThread
