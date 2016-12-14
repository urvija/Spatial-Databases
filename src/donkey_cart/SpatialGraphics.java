package donkey_cart;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.QuadCurve2D;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JPanel;
import java.io.*;
import java.util.*;
import java.sql.*;
import oracle.sql.*;

public class SpatialGraphics extends JPanel {

	private static final long serialVersionUID = 1L;
	String query;

	public SpatialGraphics(String qry) {
		query = qry;
	}

	DBConnection dbObj = new DBConnection();

	public void getData(Graphics g) throws ClassNotFoundException, SQLException {
	
		Connection con = dbObj.getConnection();
//		query = "SELECT * from zing_table";

		Statement stmt = null;
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		while (rs.next()) {
		
			java.sql.Struct o1 = (java.sql.Struct) rs.getObject("shape");
			oracle.sql.ARRAY oa3 = (oracle.sql.ARRAY) o1.getAttributes()[3];
			oracle.sql.ARRAY oa4 = (oracle.sql.ARRAY) o1.getAttributes()[4];
			String sss = oa3.getBaseTypeName();
			System.out.println(sss);
			System.out.println(oa3.length());
			int[] ia3 = oa3.getIntArray();
			int[] ia4 = oa4.getIntArray();
			int x[] = new int[40];
			int y[] = new int[40];

			int type = ia3[1];
			int interpretation = ia3[2];
			System.out.println("The type is: " + type + " and interpretation: "
					+ interpretation);
			int k = 0;
			int l = 0;
			for (int j = 0; j < ia4.length; j++) {
				if (j % 2 == 0) {
					x[k] = ia4[j]; // * 300 / 50;
					System.out.print(x[k] + " x ");
					k++;
				} 
				else {
					y[l] = (500 - ia4[j]); // * 300 / 50;
					System.out.print(y[l] + " y ");
					l++;
				}
			}
			System.out.println();

//			g.setColor(Color.GREEN);

			if (type == 1003 && interpretation == 1) {
				g.drawPolygon(x, y, ia4.length / 2);
			} 
			else if (type == 1003 && interpretation == 4) {
				int width = ((x[1] - x[2]) > 0) ? 2 * (x[1] - x[2])
						: 2 * (x[2] - x[1]);
				int hieght = width;
				int X = x[0];
				int Y = y[2]; // - hieght / 2;
				g.drawOval(X, Y, width, hieght); // x,y,w,h
			} 
			else if (type == 1003 && interpretation == 3) {
				int X = x[0];
				int Y = y[1]; // left top corner
				int width = ((x[1] - x[0]) > 0) ? x[1] - x[0] : x[0] - x[1];
				int hieght = ((y[1] - y[0]) > 0) ? y[1] - y[0] : y[0] - y[1];
//				g.setColor(Color.orange);
				g.drawRect(X, Y, width, hieght); // x,y,w,h
			} 
			else if (type == 2 && interpretation == 2) {

				QuadCurve2D.Double curve1 = new QuadCurve2D.Double(x[0], y[0],
						x[1], y[1], x[2], y[2]);
				((Graphics2D) g).draw(curve1);


			} 
			else if (type == 2 && interpretation == 1) {
//				g.setColor(Color.RED);
				g.drawPolyline(x, y, ia4.length / 2);
			}
		}

		ResultSetMetaData rsmd = rs.getMetaData();
		// rs only has two columns
		int i = rsmd.getColumnCount();
		System.out.println(i);
		int jdbcType = rsmd.getColumnType(2);
		String s1 = rsmd.getColumnLabel(1);
		System.out.println(s1);
		// the JDBC type corresponding to ACCESS text has index 12: VARCHAR
		System.out.println(jdbcType);
		stmt.close();
		con.close();
	}

	@Override
	protected void paintComponent(Graphics g) {
		try {
			getData(g);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
