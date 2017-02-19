import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Timer;
import java.util.TimerTask;

public class serverMain {
	
	

	public static void main(String[] args) {

		DatagramSocket serverSocket;
		int serverSocketPort=5555;
		int bufferLength=124;
		int reliabilityNumber=2;
		int numOfPacketsReceived=0;
		
		try{
			serverSocket=new DatagramSocket(serverSocketPort);
			System.out.println("Server is running...");

//			serverSocket.setSoTimeout(5000);
			byte [] buffer=new byte[bufferLength];
			while(true){
				
				DatagramPacket request =new DatagramPacket(buffer,buffer.length);
				serverSocket.receive(request);
				numOfPacketsReceived++;
				
//				String [] arrayMsg=(new String(request.getData()).split(" "));
				String clientMsg=new String(request.getData());
				System.out.println(clientMsg);

				int seqNum=request.getData()[0]&0xff;
//				System.out.println("Seqnum:"+seqNum);
				
				
				int remainder=reliabilityNumber==0?-1:numOfPacketsReceived%reliabilityNumber;
//				System.out.println("remainder:"+remainder);
//				System.out.println(remainder==0);
				
				if(reliabilityNumber==0||remainder!=0){
					System.out.println("Sending ACK:"+seqNum);
					byte [] responseMsg=new byte[1];
					responseMsg[0]=request.getData()[0];
					DatagramPacket response=new DatagramPacket(responseMsg,responseMsg.length,request.getAddress(),request.getPort());
					serverSocket.send(response);
				}else{
					System.out.println("Dropping packet because this is packet #: "+numOfPacketsReceived);
				}
				System.out.println();
				
				
			}
			
		}catch(Exception e){
			System.out.println(e);
		}
	}

}
