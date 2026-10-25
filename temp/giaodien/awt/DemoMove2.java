public class DemoMove2 extends Applet
{
//...
private Image offImage;
private Graphics offGraphics;
public void init()
{
// tao khung hinh phu
offImage = createImage(500, 500);
// lay doi tuong do hoa de ve vao khung hinh phu
offGraphics = offImage.getGraphics();
}
public void paint(Graphics g)
{
//...
offGraphics.clearRect(0, 0, 500, 500);
offGraphics.fillOval(x, y, 100, 100);
g.drawImage(offImage, 0, 0, this);
//...
}
public voidu pdate(Graphics g)
{

paint(g);

}
//...
}