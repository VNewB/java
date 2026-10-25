import java.awt.*;


class BorderLayoutDemo extends Frame
{ private Button north, south, east, west, center;

public BorderLayoutDemo(String sTitle)
{ super(sTitle);

north = new Button("North");
south = new Button("South");
east = new Button("East");
west = new Button("West");
center = new Button("Center");
this.add(north, BorderLayout.NORTH);
this.add(south, BorderLayout.SOUTH);
this.add(east, BorderLayout.EAST);
this.add(west, BorderLayout.WEST);
this.add(center, BorderLayout.CENTER);

}
public static void main(String args[])
{ Frame fr = new BorderLayoutDemo ("Border Layout Demo");

fr.pack();
fr.setVisible(true);

}

}