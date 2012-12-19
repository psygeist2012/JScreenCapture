package org.SelectionDemo;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.ws.FaultAction;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.IntellitypeListener;
import com.melloware.jintellitype.JIntellitype;

import java.awt.*;

public class SelectionDemo extends javax.swing.JFrame implements HotkeyListener, IntellitypeListener {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//VARIABLE DECLARATION
	
	
	private Image background;
	
	Image imageSave, imageOr, image1;
	// private static final Log LOG = LogFactory.getLog(SelectionDemo.class);
	 
	Robot rbt;
	Dimension dim, dimsize;
	
	JButton b, fi;
	JFrame f;
	
	String file_name;
	int wtFlag = 0;
	
	private static final int CTRL_SHIFT_C = 90;
	final JFileChooser fc = new JFileChooser();
	
   static SelectionDemo d;
   //initialization    
   public void initComponents() throws AWTException {
    	
	  
	   
    	 f = new JFrame("JScreenCapture");   	
    	 
    	 f.setSize(200, 90);		 
    	 f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		 f.setVisible(true);		 
		 f.toFront();		 
		 f.setLayout(new FlowLayout());
		 f.setAlwaysOnTop(true);
		 
		 b = new JButton("Grab");
		 f.add(b);
		 
		 fi = new JButton("File");
		 f.add(fi);
		 
		 //Hotkey listner instance creation
		 JIntellitype.getInstance().addHotKeyListener(this);
         JIntellitype.getInstance().addIntellitypeListener(this);
		 
		 
	      // first check to see if an instance of this application is already
	      // running, use the name of the window title of this JFrame for checking
	      if (JIntellitype.checkInstanceAlreadyRunning("JIntellitype Test Application")) {
	        System.out.println("An instance of this application is already running");
	         System.exit(1);
	      }
	      
	      // next check to make sure JIntellitype DLL can be found and we are on 
	      // a Windows operating System
	      if (!JIntellitype.isJIntellitypeSupported()) {
	    	  System.out.println("JIntellitype.DLL is not found in the path or this is not Windows 32bit OS.");
	         System.exit(1);
	      }

		 
		f.requestFocusInWindow();
		
		
	
		 
		 b.setMnemonic(KeyEvent.VK_C);
		 //background = null;
			 
		 b.addActionListener(new java.awt.event.ActionListener() 
		 {
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
                try { jButton2ActionPerformed(evt);
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
                btnRegisterHotKey_actionPerformed(evt);
                //System.out.println("atleast it went here");
            }
        });
		     
		 fi.addActionListener(new java.awt.event.ActionListener() 
		 {
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
                try { 
                	
                	DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
                	Date date = new Date();
                	File file = new File("d:\\" + dateFormat.format(date));
                	fc.setSelectedFile(file); 
                	FileNameExtensionFilter ft = new FileNameExtensionFilter( "Text Files", "doc", "Doc Files", "docx" );
                	fc.addChoosableFileFilter(ft);
                	int ret =fc.showSaveDialog(f);
                	
                	if (ret == javax.swing.JFileChooser.APPROVE_OPTION){
                		File saved_file = fc.getSelectedFile();
                		file_name = saved_file.toString();
                		System.out.println(file_name);
                		wtFlag = 1;
                	}
                	
                	System.out.println("Returned value" + ret);
                	jButton2ActionPerformed(evt);
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
                btnRegisterHotKey_actionPerformed(evt);
                //System.out.println("atleast it went here");
            }
        });
		 
		 
    	setUndecorated(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        pack();
    }


//MOUSE CLICK EVENT
   public void jButton2ActionPerformed(java.awt.event.ActionEvent evt) throws AWTException, InterruptedException {
        // TODO add your handling code here:
	
	   	f.setVisible(false);
	   	Thread.sleep(200);
	   	
	
	   this.add(new DrawingPanel(),BorderLayout.CENTER);
	   //this.getCursor();
	   Cursor hourglassCursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
	   setCursor(hourglassCursor);
	    
	   
	   try {
		   	//System.out.println("inside button action robot");
		   	 rbt = new Robot( );
        
		   	 dimsize = Toolkit.getDefaultToolkit().getScreenSize( );
		   	 this.setSize((int)dimsize.getWidth(),(int) dimsize.getHeight());
        
		   	 Toolkit tk = Toolkit.getDefaultToolkit( );
		   	 dim = tk.getScreenSize( );
		   	 background = rbt.createScreenCapture(new Rectangle(0,0,(int)dim.getWidth( ),(int)dim.getHeight( )));
		   	 rbt = null;
	   	  } 
	   catch (Exception ex) 
	   	  {
		   	ex.printStackTrace( );
	   	  }
	
	d.setVisible(true);
	
	//System.out.println("Grab clicked");

}
    
   public void jButton2ActionPerformedonKey() throws AWTException, InterruptedException {
       // TODO add your handling code here:
	
	   	f.setVisible(false);
	   	Thread.sleep(100);
	   	
	   /*	if (wtFlag == 1){
	   		Thread.sleep(500);
	   	}*/
	
	   this.add(new DrawingPanel(),BorderLayout.CENTER);
	   //this.getCursor();
	   Cursor hourglassCursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
	   setCursor(hourglassCursor);
	    
	   
	   try {
		   	//System.out.println("inside button action robot");
		   	 rbt = new Robot( );
       
		   	 dimsize = Toolkit.getDefaultToolkit().getScreenSize( );
		   	 this.setSize((int)dimsize.getWidth(),(int) dimsize.getHeight());
       
		   	 Toolkit tk = Toolkit.getDefaultToolkit( );
		   	 dim = tk.getScreenSize( );
		   	 background = rbt.createScreenCapture(new Rectangle(0,0,(int)dim.getWidth( ),(int)dim.getHeight( )));
		   	 rbt = null;
	   	  } 
	   catch (Exception ex) 
	   	  {
		   	ex.printStackTrace( );
	   	  }
	
	d.setVisible(true);
	
	//System.out.println("Grab clicked");
	//repaint();
}   
   
   
//SelectionDemo constructor	
    public SelectionDemo() throws AWTException 
    {
        initComponents();        
    }
    
   
    class DrawingPanel extends JPanel implements MouseMotionListener, MouseListener{

        Rectangle selection, selection1;
        Point anchor;
       // public Image background1;
        
        float alpha = 0.10f;
	    int type = AlphaComposite.SRC_OVER; 
	    
	 

        public DrawingPanel()
        {
        	//System.out.println("Inside DrawingPanel constructor");
            addMouseListener(this);
            addMouseMotionListener(this);
            //updateBackgroundInner( );
        }

       /* public void updateBackgroundInner( ) {
        }*/
        
        @Override
        protected void paintComponent(Graphics g) {
        	
            super.paintComponent(g);
            Point pos = this.getLocationOnScreen( );
    	    Point offset = new Point(-pos.x,-pos.y);
    	    
    	    
    	    AlphaComposite composite =  AlphaComposite.getInstance(type, alpha);
    	    Color color = new Color(1, 1, 0, alpha);
    	    
    	  
		           
    	    g.drawImage(background,offset.x,offset.y,null);
             //System.out.println("Drawn the image");
            
    	   
    	    
            if (selection!=null){
                Graphics2D g2d = (Graphics2D)g;
                            
                
                System.out.println("Aphal inside paintcomponent is" + alpha);         
                g2d.setColor(Color.RED);
              
                g2d.draw(selection);
               
                g2d.setColor(color);
                g2d.fill(selection);
                System.out.println("inside paint component");
               
                
                
            }
            
            
        }

        
        public void mousePressed(MouseEvent e) {
        	       	
            anchor = e.getPoint();
            selection = new Rectangle(anchor);
           
            
        }

        public void mouseDragged(MouseEvent e) {
        	
        	
        	selection.setRect( Math.min(anchor.x,e.getX()), Math.min(anchor.y,e.getY()),Math.abs(e.getX()-anchor.x), Math.abs(e.getY()-anchor.y));
        	//selection1.setBounds( (int)Math.min(anchor.x,e.getX()), (int)Math.min(anchor.y,e.getY()),(int)Math.abs(e.getX()-anchor.x), (int)Math.abs(e.getY()-anchor.y));   
        	
            repaint();
            //alpha = 0.00f;
           
        }

        public void mouseReleased(MouseEvent e) {
        	
        	int x=(int)(anchor.x), x1 = (int)e.getX(), y=(int)(anchor.y),y1 = (int)e.getY(), w=(int)Math.abs(e.getX()-anchor.x),h=(int)Math.abs(e.getY()-anchor.y);
        	System.out.println("before if else" + x + " " + y + " "+ x1 + " " + y1);
        	
        	
        	if (x > x1)
        	{
        		x = x1;
        		         				 
        	}
        	
        	if (y > y1)
        	{       		
        		y = y1;
        		
        	}
        	
        	System.out.println("after if else" + x + " " + y + " "+ w + " " + h);
	           
        	selection.setRect((anchor.x+1),(anchor.y+1),Math.abs(e.getX()-anchor.x-1),Math.abs(e.getY()-anchor.y-1));
        	
        	BufferedImage desu = (BufferedImage) background;
        	
        	BufferedImage dest = desu.getSubimage(x, y, w, h);
        	   	
    	   
        	
				try {
					ImageIO.write(dest, "PNG", new File("D:\\screenShot.png"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					//System.out.println("failed saving");
				}
				
			
			
			//System.out.println("Done loading");
			
			Clipboard clipboard = getToolkit().getSystemClipboard();
			
					
			
			ImageTransferable imageTransferable = new ImageTransferable(dest);
			//System.out.println("stuck before clipboard");
			
				   try {
					clipboard.setContents(imageTransferable, null);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					//System.out.println("Clipboard got exception assole");
					e1.printStackTrace();
				}
				   
				   char unic = '\u27D0';
            System.out.println("stuck after clipboard" + unic);
			
            		imageTransferable = null;
				    imageOr = null;
				    imageSave = null;
				    image1 = null;
				    background.flush();
				    background = null;
				    desu.flush();
				    desu = null;
				    dest.flush();
				    dest = null;
				   	anchor = null;		    
				    selection = null;
				    				  				    
				    d.setVisible(false);
				    f.setVisible(true);
				    
				    //System.out.println("Mouse released");
            
        }

      //clipboard code goes down there       
     
        
         class ImageTransferable implements Transferable
        {
            private Image image;

            public ImageTransferable (Image image)
            {
                this.image = image;
                
                //System.out.println("Inside Transferable");
            }

            public Object getTransferData(DataFlavor flavor)
                throws UnsupportedFlavorException
            {
                if (isDataFlavorSupported(flavor))
                {
                   //System.out.println("Inside idDataFlavor Supported true");
                   return image;
                }
                else
                {
                	//System.out.println("Inside idDataGlvor false");
                    throw new UnsupportedFlavorException(flavor);
                }
            }

            public boolean isDataFlavorSupported (DataFlavor flavor)
            {
                return flavor == DataFlavor.imageFlavor;
            }

            public DataFlavor[] getTransferDataFlavors ()
            {
                return new DataFlavor[] { DataFlavor.imageFlavor };
            }

			
            
            
        }


        // unused
        public void mouseMoved(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
    }

 




	
	
	
//Main starts here	
	 public static void main(String args[]) {
	       java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
		 
	                try 
	                {
	                	d = new SelectionDemo();
						
						//d.setVisible(true);
											
						
					} catch (AWTException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                
	              // JIntellitype.getInstance().cleanUp();
	            	//System.exit(0);
	            }
	        });
	    }


	@Override
	public void onIntellitype(int aCommand) {
		// TODO Auto-generated method stub
		
		//System.out.println("inside shortcut verbiage");
		switch (aCommand) {
	      case JIntellitype.APPCOMMAND_BROWSER_BACKWARD:
	         //System.out.println("BROWSER_BACKWARD message received " + Integer.toString(aCommand));
	         break;
	      case JIntellitype.APPCOMMAND_BROWSER_FAVOURITES:
	    	  //System.out.println("BROWSER_FAVOURITES message received " + Integer.toString(aCommand));
	         break;
	      case JIntellitype.APPCOMMAND_BROWSER_FORWARD:
	    	  //System.out.println("BROWSER_FORWARD message received " + Integer.toString(aCommand));
	         break;
	      case JIntellitype.APPCOMMAND_BROWSER_HOME:
	    	  //System.out.println("BROWSER_HOME message received " + Integer.toString(aCommand));
	         break;
	      case JIntellitype.APPCOMMAND_BROWSER_REFRESH:
	    	  //System.out.println("BROWSER_REFRESH message received " + Integer.toString(aCommand));
	         break;
	      case JIntellitype.APPCOMMAND_BROWSER_SEARCH:
	    	  //System.out.println("BROWSER_SEARCH message received " + Integer.toString(aCommand));
	         break;
	      case JIntellitype.APPCOMMAND_BROWSER_STOP:
	    	  //System.out.println("BROWSER_STOP message received " + Integer.toString(aCommand));
	         break;
	      case JIntellitype.APPCOMMAND_LAUNCH_APP1:
	    	  //System.out.println("LAUNCH_APP1 message received " + Integer.toString(aCommand));
	         break;
	      case JIntellitype.APPCOMMAND_LAUNCH_APP2:
	    	  //System.out.println("LAUNCH_APP2 message received " + Integer.toString(aCommand));
	         break;
	      case JIntellitype.APPCOMMAND_LAUNCH_MAIL:
	    	  //System.out.println("LAUNCH_MAIL message received " + Integer.toString(aCommand));
	         break;
	      case JIntellitype.APPCOMMAND_MEDIA_NEXTTRACK:
	    	  //System.out.println("MEDIA_NEXTTRACK message received " + Integer.toString(aCommand));
	         break;
	      case JIntellitype.APPCOMMAND_MEDIA_PLAY_PAUSE:
	    	  //System.out.println("MEDIA_PLAY_PAUSE message received " + Integer.toString(aCommand));
	         break;
	      case JIntellitype.APPCOMMAND_MEDIA_PREVIOUSTRACK:
	    	  //System.out.println("MEDIA_PREVIOUSTRACK message received " + Integer.toString(aCommand));
	         break;
	      case JIntellitype.APPCOMMAND_MEDIA_STOP:
	    	  //System.out.println("MEDIA_STOP message received " + Integer.toString(aCommand));
	         break;
	      case JIntellitype.APPCOMMAND_VOLUME_DOWN:
	    	  //System.out.println("VOLUME_DOWN message received " + Integer.toString(aCommand));
	         break;
	      case JIntellitype.APPCOMMAND_VOLUME_UP:
	    	  //System.out.println("VOLUME_UP message received " + Integer.toString(aCommand));
	         break;
	      case JIntellitype.APPCOMMAND_VOLUME_MUTE:
	    	  //System.out.println("VOLUME_MUTE message received " + Integer.toString(aCommand));
	         break;
	      default:
	    	  //System.out.println("Undefined INTELLITYPE message caught " + Integer.toString(aCommand));
	         break;
	      }
		
	}


	@Override
	public void onHotKey(int aIdentifier) {
		// TODO Auto-generated method stub
		//System.out.println("inside hotkey identification");
	if(aIdentifier == 90)
	{	//System.out.println("WM_HOTKEY message received " + Integer.toString(aIdentifier));
	   try {
		   f.setVisible(false);
		   Thread.sleep(200);
	jButton2ActionPerformedonKey();
		
	} catch (AWTException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
		//System.out.println("inside onHotKey");
	}
	}
	private void btnRegisterHotKey_actionPerformed(ActionEvent aEvent) {
	      //LOG.debug("RegisterHotKey");

		//System.out.println("inside assigning keys");
	      // assign the WINDOWS+A key to the unique id 88 for identification
	      //JIntellitype.getInstance().registerHotKey(WINDOWS_A, JIntellitype.MOD_WIN, (int) 'A');
	      //JIntellitype.getInstance().registerHotKey(ALT_SHIFT_B, JIntellitype.MOD_ALT + JIntellitype.MOD_SHIFT, (int) 'B');
	      JIntellitype.getInstance().registerSwingHotKey(CTRL_SHIFT_C, Event.ALT_MASK, (int) 'C');
	      
	      // use a 0 for the modifier if you just want a single keystroke to be a hotkey
	      //JIntellitype.getInstance().registerHotKey(PRINT_SCREEN, 0, 44);
	      //JIntellitype.getInstance().registerHotKey(F9, 0, 120);
	      // clear the text area
	      //textArea.setText("");
	      //System.out.println("RegisterHotKey WINDOWS+A was assigned uniqueID 88");
	      //System.out.println("RegisterHotKey ALT+SHIFT+B was assigned uniqueID 89");
	      //System.out.println("RegisterHotKey CTRL+SHIFT+C was assigned uniqueID 90");
	      //System.out.println("RegisterHotKey PRINT_SCREEN was assigned uniqueID 91");
	      //System.out.println("RegisterHotKey F9 was assigned uniqueID 92");
	      //System.out.println("Press WINDOWS+A or ALT+SHIFT+B or CTRL+SHIFT+C in another application and you will see the debug output in the textarea.");
	   }
	
	
	

	
}