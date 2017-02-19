import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class clientMain {

	public static void main(String[] args) throws IOException {
//		FileInputStream in = new FileInputStream(filename);
//		in.rea
		// TODO Auto-generated method stub
		
		DatagramSocket clientSocket;
		String serverIP=args[0];
		int serverPort=Integer.parseInt(args[1]);
		int clientPort=5558;//Integer.parseInt(args[2]);
		String fileName=args[3];
		int bufferLength=124;
		int sequenceSize=2;
		byte [] byteArray=new byte[124];
		File file = new File("src/supermarket.txt");
		int numBytes=(int) file.length();

		
//		try {		

			clientSocket=new DatagramSocket(clientPort);
			clientSocket.setSoTimeout(500);
    		InetAddress host=InetAddress.getByName(serverIP);

    		
			int seqNumber=shared.getExpectedSequenceNumber();
			FileInputStream in = new FileInputStream("src/supermarket.txt");
			int currentByte=0;
			int bytesLeft;
			boolean resendPacket=false;
			while(currentByte<(numBytes-1)){
				seqNumber=shared.getExpectedSequenceNumber();
				if(!resendPacket){
					byteArray=new byte[124];

					byteArray[0]=(byte) shared.getExpectedSequenceNumber();
					bytesLeft=numBytes-currentByte;

					if(bytesLeft>bufferLength){
						in.read(byteArray, 1, bufferLength-2);
						currentByte=currentByte+bufferLength-2;
						int c=0;

					}else{	
						in.read(byteArray, 1, bytesLeft-1);
						currentByte=currentByte+bytesLeft-1;
					}
				}
//				resendPacket=false;
				
				String clientMsg=new String(byteArray);
				System.out.println(clientMsg);

				DatagramPacket request=new DatagramPacket(byteArray,byteArray.length,host,serverPort);

				System.out.println("Sending Packet: "+seqNumber);
				clientSocket.send(request);
				//to receive a response from server
				byte [] buffer=new byte[bufferLength];
				DatagramPacket response=new DatagramPacket(buffer, buffer.length);
				
				try{
					clientSocket.receive(response);
					int responseACK=response.getData()[0]&0xff;
					System.out.println("Receiving ACK: "+ responseACK);
//					resendPacket=responseACK==shared.getExpectedSequenceNumber()?false:true;
					if(responseACK==shared.getExpectedSequenceNumber()){
						resendPacket=false;
						shared.incrementExpectedSequenceNumber();

						
					}else{
						resendPacket=true;
					}
					
				}catch(SocketTimeoutException e){
					resendPacket=true;
					System.out.println("Timeout, set resendPacket flag to true");
				}
				
				System.out.println();
				
				
			}
			 					            
			clientSocket.close();
//		} catch (Exception e) {
			// TODO Auto-generated catch block
//			System.out.println("Timeout reached!");
//			e.printStackTrace();
//		} 
		

	}

}
