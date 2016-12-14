package donkey_cart;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class StartingPoint extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton btn1;
	private JButton btn2;
	private JButton btn3;
	private JButton btn4;
	private JButton btn5;
	private JButton btn6;
	private JButton btn7;
	private JButton btn8;
	private JButton btn9;
	private JButton btn10;
	private JButton btn11;
	private JButton btn12;
	private JButton btnExit;
	private static StartingPoint spg;

	public StartingPoint() {
	
		setSize(150, 500);
		setVisible(true);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(1100, 50);
		btn1 = new JButton("See full Image");
		btn2 = new JButton("Add Stool");
		btn3 = new JButton("Touch");
		btn4 = new JButton("Contains by EYE");
		btn5 = new JButton("Covered by EYE");
		btn6 = new JButton("Neighbours of Bow");
		btn7 = new JButton("Filer of Head");
		btn8 = new JButton("Inside");
		btn9 = new JButton("OVER LAP BY DISJOINT");
		btn10 = new JButton("OVERLAPS");
		btn11 = new JButton("Delete Sun");
		btn12 = new JButton("Update Sun");
		btnExit = new JButton("Exit");
		add(btn1);
		add(btn2);
		add(btn3);
		add(btn4);
		add(btn5);
		add(btn6);
		add(btn7);
		add(btn8);
		add(btn9);
		add(btn10);
		add(btn11);
		add(btn12);
		add(btnExit);

		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		btn4.addActionListener(this);
		btn5.addActionListener(this);
		btn6.addActionListener(this);
		btn7.addActionListener(this);
		btn8.addActionListener(this);
		btn9.addActionListener(this);
		btn10.addActionListener(this);
		btn11.addActionListener(this);
		btn12.addActionListener(this);

		btnExit.addActionListener(this);
	}

	public static void main(String[] args) {
		spg = new StartingPoint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFrame frame = new JFrame();
		SpatialGraphics spg = null;
		//frame.add(spg);
		
		try {
			
			if (e.getSource() == btn1) // show full image
			{
				spg = new SpatialGraphics("SELECT * from donkey_cart");
			}
			
			if (e.getSource() == btn2) // insert stool
			{
				Connection con = DBConnection.getConnection();
				Statement stmt = null;
				String query = "INSERT INTO donkey_cart VALUES( 301, 'stool', "
						+ "MDSYS.SDO_GEOMETRY (2003, NULL, NULL, MDSYS.SDO_ELEM_INFO_ARRAY(1, 1003, 1), "
						+ "MDSYS.SDO_ORDINATE_ARRAY(120,0, 120,50, 200,50, 200,0, 120, 0)))";
				stmt = con.createStatement();
				int i = stmt.executeUpdate(query);

				if (i >= 1)	{
					JOptionPane.showMessageDialog(null, "Congrats");
					spg = new SpatialGraphics("SELECT * from donkey_cart");
				}
			}
			
			if(e.getSource() == btn3) // Touches to the Bow
			{
				spg = new SpatialGraphics("SELECT * FROM donkey_cart z1,donkey_cart z2 WHERE z2.NAME='eye' "
						+ "AND z1.NAME<>'eye' AND SDO_RELATE"
						+ "(z1.shape,z2.shape,'MASK=TOUCH') = 'TRUE'");
			}

			if(e.getSource() == btn4) // Contains
			{
				spg = new SpatialGraphics("SELECT * FROM donkey_cart z1,donkey_cart z2 WHERE z2.NAME='eye' "
						+ "AND z1.NAME<>'eye' AND SDO_CONTAINS"
						+ "(z1.shape,z2.shape) = 'TRUE'");
			}

			if(e.getSource() == btn5) // Covered
			{
				spg = new SpatialGraphics("SELECT * FROM donkey_cart z1,donkey_cart z2 WHERE z2.NAME='eye' "
						+ "AND z1.NAME<>'eye' AND SDO_COVEREDBY"
						+ "(z1.shape,z2.shape) = 'TRUE'");
			}

			if(e.getSource() == btn6) // Nearest
			{
				spg = new SpatialGraphics("SELECT * FROM donkey_cart z1,donkey_cart z2 WHERE z2.NAME='eye' "
						+ "AND z1.NAME<>'eye' AND SDO_NN"
						+ "(z1.shape,z2.shape,'sdo_num_res=5') = 'TRUE'");
			}

			if(e.getSource() == btn7) // Filter
			{
				spg = new SpatialGraphics("SELECT * FROM donkey_cart z1,donkey_cart z2 WHERE z2.NAME='eye' "
						+ "AND z1.NAME<>'eye' AND SDO_FILTER"
						+ "(z1.shape,z2.shape) = 'TRUE'");
			}

			if(e.getSource() == btn8) // Inside
			{
				spg = new SpatialGraphics("SELECT * FROM donkey_cart z1,donkey_cart z2 WHERE z2.NAME='ears' "
						+ "AND z1.NAME<>'ears' AND SDO_INSIDE"
						+ "(z1.shape,z2.shape) = 'TRUE'");
			}

			if(e.getSource() == btn9) // OVERLAPBDYDISJOINT
			{
				spg = new SpatialGraphics("SELECT * FROM donkey_cart z1,donkey_cart z2 WHERE z2.NAME='eye' "
						+ "AND z1.NAME<>'eye' AND SDO_OVERLAPBDYDISJOINT"
						+ "(z1.shape,z2.shape) = 'TRUE'");
			}

			if(e.getSource() == btn10) // OVERLAPS
			{
				spg = new SpatialGraphics("SELECT * FROM donkey_cart z1,donkey_cart z2 WHERE z2.NAME='eye' "
						+ "AND z1.NAME<>'eye' AND SDO_OVERLAPS"
						+ "(z1.shape,z2.shape) = 'TRUE'");
			}

			if(e.getSource() == btn11) // OVERLAPS
			{
				Connection con = DBConnection.getConnection();
				Statement stmt = null;
				String query = "DELETE FROM donkey_cart WHERE ID=303";
				stmt = con.createStatement();
				int i = stmt.executeUpdate(query);

				if (i >= 1) {
					JOptionPane.showMessageDialog(null, "Congrats");
					spg = new SpatialGraphics("SELECT * from donkey_cart");
				}
			}
			
			if(e.getSource() == btn12) // update sun
			{
				Connection con = DBConnection.getConnection();
				Statement stmt = null;
				String query = "UPDATE  SET shape =  "
						+ "MDSYS.SDO_GEOMETRY (2003, NULL, NULL, "
						+ "MDSYS.SDO_ELEM_INFO_ARRAY(1, 1003, 4),"
						+ "MDSYS.SDO_ORDINATE_ARRAY(350,320, 280,320, 250,395)) WHERE ID = 303 ";
				stmt = con.createStatement();
				int i = stmt.executeUpdate(query);

				if (i >= 1) {
					JOptionPane.showMessageDialog(null, "Congrats");
					spg = new SpatialGraphics("SELECT * from donkey_cart");
				}
			}

			if(e.getSource() == btnExit) // nearest to the Bow
			{
				System.exit(0);
			}

		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}

		frame.add(spg);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(1000, 700);
	}
}
