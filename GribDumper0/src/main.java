





public class main {

	   public static void main (String[] args){
		    /*JFrame frame = new JFrame("FrameDemo");
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.getContentPane().add(new paneGeneral(), BorderLayout.CENTER);
		    frame.pack();
		    frame.setSize(300,300);
		    frame.setVisible(true);*/
		    
		   /* int v = 1858;
		    System.out.println("dec: " + v);
		    System.out.println("bin: " + Integer.toBinaryString(v).length());
		    //v = (v * 2) & 0xffffffff;
		    System.out.println(Integer.toBinaryString(v).length());
		    
		    v = (v & 0x3FFFFFFF) * -1;
		    System.out.println("*********RES***********");
		    System.out.println("dec: " + v);
		    System.out.println("bin: " + Integer.toBinaryString(v));*/
		    
		    

		
			int chessBoard[][] = new int[10][10];
	
		    String output = "";
		    int sizeLine = 20;
		    int length = 5;
		    
		    for (int line = 0; line < sizeLine; line++) {
			    	for (int column = 0; column < length; column++) {
			    		if(line % 2 == 0) {
							int x = line * length + column;
							output += " " + x;
				    	} else {
				    		int x = (line + 1) * length - (column + 1);
							output += " " + x;
				    	}
					}
		    	output += "\n";
			}
		    System.out.println(output);
		    
		   }
}
