import java.io.File;
import java.io.FileInputStream;

public class filein {
	public static void main(String[] args) {
		try{
			File file = new File("src/supermarket.txt");
			FileInputStream in = new FileInputStream("src/supermarket.txt");
			int numBytes=(int) file.length();
			int currentByte=0;
			byte[] byteArray=new byte[124];
			int bufferSize=124;
			int bytesLeft;
			System.out.println("total"+numBytes);
			while(currentByte<numBytes){
//				System.out.println(currentByte);
//				System.out.println(currentByte+bufferSize);
//				System.out.println(byteArray.length);
				bytesLeft=(numBytes-1)-currentByte;
				System.out.println("bleft"+bytesLeft);
				System.out.println("buffer"+bufferSize);

				if(bytesLeft>bufferSize){
					System.out.println("yeah");
					in.read(byteArray, 0, bufferSize);

				}else{
					System.out.println("yo");
					
					in.read(byteArray, 0, bytesLeft);
					in.read

				}
				
				currentByte=currentByte+bufferSize;
			}
			System.out.println("yeah");
		}catch(Exception e ){
			e.printStackTrace();
		}
		

	}
}
