package edu.ucdavis.UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import edu.ucdavis.ecs160.Parameters;

import android.content.Context;
import android.util.Log;

public class ClientThread implements Runnable{
	private static final String TAG = "ClientThread";
	public final int HOSTPORT = 6060; //redir from
	public final String CLIENTADDRESSNAME = "10.0.2.2";
	private Context context;
	private Charset usedCS = Charset.forName(Parameters.CHARSET);
	private final CharsetDecoder decoder = usedCS.newDecoder();
	private final CharsetEncoder encoder = usedCS.newEncoder();
	
	public void setContext(Context appContext) {
		context = appContext;
	}
	
	public void run() {
		try {
			//Setup the socket with a random port
			InetAddress HostAddress = InetAddress.getByName(CLIENTADDRESSNAME);
			DatagramSocket socket = new DatagramSocket();
			
			//Setup the DatagramPacket to send and receive package
		    ByteBuffer bbuf = encoder.encode(CharBuffer.wrap("Testing String."));
		    byte[] sendData = bbuf.array();
			DatagramPacket packet = new DatagramPacket(
					sendData, sendData.length, HostAddress, HOSTPORT);
			Log.v(TAG, "Converted string (using " + usedCS.displayName() + "): " + sendData.toString());
			Log.v(TAG,"Socket Setuped");
			
			//Verify the sending message can be decode
			CharBuffer verify = decoder.decode(bbuf.wrap(packet.getData()));
			Log.v(TAG, "Start sending: " + verify.toString());
			
			socket.send(packet); //this line hits invalid argument in socket exception
			Log.v(TAG, "Packet Sent!");
			
			Log.v(TAG, "Start Listening...");
			socket.receive(packet);
			Log.v(TAG, "Packet Received!");
			
			//Decode the package
			CharBuffer received = decoder.decode(bbuf.wrap(packet.getData()));
			Log.v(TAG, received.toString());
			
		}catch(Exception e){
			Log.v(TAG, e.toString());
		}
	}//run
}//class ClientThread
