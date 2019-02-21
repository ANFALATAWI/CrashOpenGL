package org.yourorghere;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import static javax.media.opengl.GL.GL_LINES;
import static javax.media.opengl.GL.GL_POLYGON;
import static javax.media.opengl.GL.GL_QUADS;
import static javax.media.opengl.GL.GL_QUAD_STRIP;
import static javax.media.opengl.GL.GL_TRIANGLE_FAN;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.IOException;



/**
 * author: Anfal Alatawi
 */
public class Crash implements GLEventListener {

Texture tex; // Texture object

    public static void main(String[] args) {
        Frame frame = new Frame("Crash Bandicoot");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Crash());
        frame.add(canvas);
        frame.setSize(640, 480);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        try {
            // loading the texture:
            tex = TextureIO.newTexture(new File("t3.jpg"), true);
        }
        catch(IOException ex){
            System.err.println(ex);
        }
        
        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(1.0f, 1.0f, 164/255f, 0.5f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!
        
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();

        /*
         These are different "views" of the character, each view was used to focus on
         some part of the character, you can use them to see minor details, just comment
         them in.
         */

        // Move the "drawing cursor" around
        // Overview cursor
        gl.glTranslatef(0f, -0.4f, -15f);

        // -- Crash Overview cursor
        //gl.glTranslatef(0f, -0.9f, -12f);
        
        // -- Shoe Detail cursor
        //gl.glTranslatef(2f, 2.0f, -5f);
        
        // -- Mouth Detail cursor
        //gl.glTranslatef(0f, -2.0f, -5f);
        
        // -- Face Detail cursor
        //gl.glTranslatef(0f, -3.5f, -5f);
        
        // -- Head Detail cursor
        //gl.glTranslatef(0f, -4f, -5f);
        
        // -- Arm Detail cursor
        //gl.glTranslatef(2f, -1f, -9f);
        
        // -- Hand Detail cursor
        //gl.glTranslatef(2f, 1f, -5f);
         
        // Crash's code -----------------------------------------------------------------------
        
        // Drawing the background:
        // Setting the background color
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        // Turning the texture on
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0,1); //tex
        gl.glVertex2f(-10f,-6f);
        gl.glTexCoord2f(1,1); //tex
        gl.glVertex2f(10f,-6f);
        gl.glTexCoord2f(1,0);//tex
        gl.glVertex2f(10f,6.5f);
        gl.glTexCoord2f(0,0);//tex
        gl.glVertex2f(-10f,6.5f);
        gl.glEnd();
        // Turning the texture off
        gl.glDisable(GL.GL_TEXTURE_2D);

        /*
        Since Crash is a symmetric object around the Y axis, we can draw the left half
        of his charachter only and then mirror that around Y (by multiplying the x value by -)
        for every vertex point.
        This was done in seperate methods.
        */

        // Crash's pants:------------------------------------
        pants(gl);
        
        // Crash's shoes:------------------------------------
        shoes(gl);
        
        // Crash's legs:-------------------------------------
        legs(gl);
        
        // Crash's torso:------------------------------------
        torso(gl);
        
        // Crash's ears:-------------------------------------
        ears(gl);
       
        // Crash's Arms:-------------------------------------
        arms(gl);
        
        // Crash's Hands:------------------------------------
        hands(gl);

        // Crash's face:-------------------------------------
        face(gl);
        
         // Crash's Hair:------------------------------------
        hair(gl);
        
        // Crash's Jaw:--------------------------------------
        jax(gl);
        
        // Crash's Mouth:------------------------------------
        mouth(gl);
        
        // Crash's nose--------------------------------------
        nose(gl);
        
        // Crash's Eyes:-------------------------------------
        eyes(gl);
        
        // Crash's Eyebrows:---------------------------------
        eyebrows(gl);
        
        // End of my code
        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    /*
    All the following methods draw the character part by part.
    */

    public static void pants(GL gl)
    {
    	float[][] pants = {{0f,0f},
                        {-0.7f,0f},
                        {-1.4f,-1.2f},
                        {-1.8f,-2.4f},
                        {-0.5f,-2.4f},
                        {-0.4f,-1.5f},
                        {0f,-0.8f}
        };

        // Setting the color & drawing
        gl.glColor3d(0f, 0f, 1f);
        drawSymmetricPoly(pants, gl);
    }
    public static void shoes(GL gl)
    {
    	// Upper part
        float[][] shoe = {{-1.3f,-2.8f},
                        {-1.5f,-3.2f},
                        {-0.8f,-3.2f},
                        {-1f,-2.8f}
        };
        // Lower part
        float[][] shoe2 = {
             {-1.5f,-3.2f},
                        {-1.8f,-3.3f},
                        {-2.1f,-3.6f},
                        {-2.2f,-4f},
                        {-0.4f,-4f},
                        {-0.5f,-3.6f},
                        {-0.8f,-3.2f}
        };
        // Shoe details
        float[][] shoeDetails = {{-1.2f,-2.85f}, // subject to change y coordinate
                        {-1.4f,-3.2f},
                        {-1.1f,-3.2f},
                        {-1.1f,-2.85f}
        };
        // Crash's Shoelace
        float[][] shoe2ndDetails = {{-1.8f,-3.4f},//1 // subject to change y coordinate
                        {-1.75f,-3.3f}, //2
                        {-1.55f,-3.3f}, //3
                        {-1.5f,-3.25f}, //4
                        {-1.25f,-3.4f}, //5
                        {-1.1f,-3.3f} //6
        };
        float[][] shoe3rdDetails = {{-1.05f,-3.255f},//1 // subject to change y coordinate
                        {-1.45f,-3.205f}
        };

        // Setting the color & drawing
        gl.glColor3d(128/255f, 64/255f, 64/255f);
        drawSymmetricQuad(shoe, gl);
        drawSymmetricPoly(shoe2, gl);
        // Setting the color & drawing
        gl.glColor3d(1f, 1f, 1f);
        drawSymmetricPoly(shoeDetails, gl);
        drawSymmetricQuadStrip(shoe2ndDetails, gl);
        // changing the point size
        gl.glPointSize(0.1f);
        drawSymmetricLines(shoe3rdDetails, gl);
        gl.glPointSize(1);
    }
    public static void legs(GL gl)
    {
    	float[][] legs = {{-1.2f,-2.8f},
                        {-1.05f,-2.8f},
                        {-1.0f,-2.4f},
                        {-1.25f,-2.4f}
        };
        // Setting the color & drawing
        gl.glColor3d(1f, 84/255f, 0f);
        drawSymmetricQuad(legs, gl);
    }
    public static void torso(GL gl)
    {
    	float[][] torso = {{-0.8f,1.8f},
                        {0f,1.8f},
                        {-0.75f,1.6f},
                        {0f,1.8f},
                        {-0.7f,1.4f},
                        {0f,1.8f},
                        {-0.62f,1.2f},
                        {0f,1.8f},
                        {-0.61f,1.0f},
                        {0f,1.8f},
                        {-0.6f,0.8f},
                        {0f,1.8f},
                        {-0.59f,0.6f},
                        {0f,1.8f},
                        {-0.58f,0.4f},
                        {0f,1.8f},
                        {-0.57f,0.2f},
                        {0f,1.8f},
                        {-0.565f,0.0f},
                        {0f,1.8f},
                        {0f,0.0f},
                        {0f,1.8f},
        };
        float[][] stomach = {{-0.8f+0.25f,1.8f},
                        {0f,1.8f},
                        {-0.75f+0.25f,1.6f},
                        {0f,1.8f},
                        {-0.7f+0.25f,1.4f},
                        {0f,1.8f},
                        {-0.62f+0.25f,1.2f},
                        {0f,1.8f},
                        {-0.61f+0.25f,1.0f},
                        {0f,1.8f},
                        {-0.6f+0.25f,0.8f},
                        {0f,1.8f},
                        {-0.59f+0.25f,0.6f},
                        {0f,1.8f},
                        {-0.58f+0.25f,0.4f},
                        {0f,1.8f},
                        {-0.57f+0.25f,0.2f},
                        {0f,1.8f},
                        {-0.565f+0.25f,0.0f},
                        {0f,1.8f},
                        {0f,0.0f},
                        {0f,1.8f},
        };
        // Setting the color & drawing
        gl.glColor3d(1f, 84/255f, 0f);
        drawSymmetricQuadStrip(torso, gl);
        // Setting the color & drawing
        gl.glColor3d(223/255f, 140/255f, 0f);
        drawSymmetricQuadStrip(stomach, gl);
        
        // Drawing the belly button

        float numOfPoints = 10;
        float radius = 0.09f;
        gl.glColor3d(0f,0f,0f);
        gl.glBegin(GL.GL_POLYGON);
        for (int i = 0; i < numOfPoints; i++) {
            double angle = i * (2.0 * Math.PI / numOfPoints);
            double x = Math.cos(angle) * radius;
            double y = Math.sin(angle) * radius;
            gl.glVertex2d(x ,y + 0.2);
            
        }
        gl.glEnd();
    }
    public static void ears(GL gl)
    {
    	float[][] ears = {{-0.8f,5f}, //a
                        {-2.15f,5.7f}, //b
                        {-2.05f,5f}, //c
                        {-1.8f,4.6f},//d
                        {-1.4f,4.2f} //e
        };
        // Inner ear
        float[][] earDetail = {{-01.6f,5.19f}, //a
                        {-1.8f,5.35f}, //b
                        {-2f,5.59f}, //c
                        {-1.9f, 5f},//d
                        {-1.6f,4.55f}, //e
                        {-1.4f,4.41f}, //f
                        {-1.3f,4.5f}, //g
                        {-1.4f,4.8f} //h
        };
        // Draw
        gl.glColor3d(1f, 84/255f, 0f);
        drawSymmetricPoly(ears, gl);
        gl.glColor3d(247/255f, 197/255f, 148/255f);
        drawSymmetricPoly(earDetail, gl);

    }
    public static void arms(GL gl)
    {
    	/* 
        Since the symmetric functions don't allow for drawing gradients
        all shapes with gradients were drawn manually.
        Gradients were used to mimic shadows.
        */

        // Drawing Arms with shadow
        // Left arm
        gl.glColor3d(1f, 84/255f, 0f);
        gl.glBegin(GL.GL_QUADS);
        gl.glVertex2f(-1.4f,3.7f); //a
        gl.glVertex2f(-2.2f,1.5f); //b
        gl.glVertex2f(-1.8f,1.4f); //e
        gl.glColor3d(184/255f, 56/255f, 0f); //Shadow color
        gl.glVertex2f(-1.1f,2.4f); //f
        gl.glEnd();
        // Right arm
        gl.glColor3d(1f, 84/255f, 0f);
        gl.glBegin(GL.GL_QUADS);
        gl.glVertex2f(1.4f,3.7f); //a
        gl.glVertex2f(2.2f,1.5f); //b
        gl.glVertex2f(1.8f,1.4f); //e
        gl.glColor3d(184/255f, 56/255f, 0f); //Shadow color
        gl.glVertex2f(1.1f,2.4f); //f
        gl.glEnd();

    	float[][] lowerArms = {{-2.2f,1.5f}, //b
                        {-2.7f,0f}, //c
                        {-2.35f,0.01f}, //y
                        {-1.8f,1.4f} //e
        };
        gl.glColor3d(1f, 84/255f, 0f);
        drawSymmetricPoly(lowerArms, gl);

    }
    public static void hands(GL gl)
    {
    	float[][] hands = {{-2.63f,0.2f}, //c
                        {-3.1f,-1.3f}, //w
                        {-2.65f,-1.3f}, //p
                        {-2.35f,0.01f}, //y
                        {-2.5f,0f} //z
        };
        float[][] handDetails = {{-2.63f,0.2f}, //a
                        {-2.5f,-0.01f}, //b
                        {-2.35f,0.03f} //c
        };
        float[][] thumbs = {{-1.59f,-0.59f}, //a
                        {-2.3f,-0.59f}, //b
                        {-2.4f,-0.5f}, //c
                        {-2.6f,-0.8f}, //d
                        {-2.39f,-1.21f}, //e
                        {-1.61f,-1.21f} //f
        };
        float[][] thumbDetails = {{-2.4f,-0.5f}, //c
                        {-2.7f,-0.5f}, //x
                        {-2.95f,-0.81f}, //y
                        {-2.6f,-0.8f} //d
        };
        float[][] fingers = {
                       //{-3.1f,-1.3f}, //a
                        {-2.9f,-1.65f}, //b
                        {-2.9f,-1.97f}, //c
                        {-2.8f,-2.1f}, //d
                        {-2.6f,-2.3f}, //e
                        {-2f,-2f}, //f
                        {-2.1f,-1.9f}, //g
                        {-2.65f,-1.3f} //h
        };
        float[][] fingerDetails = {
                       {-3.1f,-1.3f}, //a
                        {-2.9f,-1.65f}, //b
                        {-2.65f,-1.3f} //h
        };
        float[][] fingerShadow = {
                       {-2.61f,-2.19f}, //alpha
                       {-2.6f,-2.3f}, //e
                       {-2f,-2f}, //f
                       {-2.1f,-1.9f} //g
        };
        gl.glColor3d(83/255f, 0f, 0f);
        drawSymmetricPoly(hands, gl);
        
        gl.glColor3d(1f, 84/255f, 0f);
        drawSymmetricTriangle(handDetails, gl);
        drawSymmetricPoly(thumbs, gl);
        drawSymmetricPoly(thumbDetails, gl);
        drawSymmetricPoly(fingers, gl);
        drawSymmetricPoly(fingerDetails, gl);
        
        gl.glColor3d(184/255f, 56/255f, 0f); //Shadow color
        drawSymmetricPoly(fingerShadow, gl);
        
        // More finger details:
        float[][] fingerLine = {
                       {-2.01f,-2f}, //f
                       {-2.5f,-2.27f},
        };
        gl.glLineWidth(4f);
        drawSymmetricLines(fingerLine, gl);
        gl.glLineWidth(1f);
    }
    public static void face(GL gl)
    {
    	float[][] face = {{0f,4.95f}, //a
                        {-1.2f,4.95f}, //b
                        {-1.5f,3.9f}, //c
                        {-1.2f,3.4f},//d
                        {0f,3.2f}
        };
        // Draw
        gl.glColor3d(1f, 84/255f, 0f);
        drawSymmetricPoly(face, gl);
        
        // Face shadow
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3d(184/255f, 56/255f, 0f);
        gl.glVertex2f(1.2f, 4.61f);
        gl.glVertex2f(-1.2f, 4.61f);
        gl.glColor3d(1f, 84/255f, 0f);
        gl.glVertex2f(-1.4f, 3.79f);
        gl.glVertex2f(1.4f, 3.79f);
        gl.glEnd();

    }
    public static void hair(GL gl)
    {
    	float[][] hair = {{-0.1f,4.9f}, //1
                        {-0f,4.85f}, //2
                        {0.2f,4.95f}, //3
                        {0.19f,5.3f},//4
                        {0.1f,5.7f}, //5
                        {0f,5.9f}, //6
                        {-0.3f,5.6f}, //7
                        {-0.2f,4.95f}, //8
        };
        float[][] hair2 = {{0.6f,6.2f}, //1
                        {0.22f,5.95f}, //2
                        {0.1f,5.7f}, //3
                        {0.19f,5.3f},//4
                        {0.39f,5.8f}, //5
        };
        // Since hair isn't symmetric:
        gl.glColor3d(252/255f, 1/255f, 0f);
        gl.glBegin(GL_POLYGON);
        for (int i = 0 ; i < hair.length ; i++)
            gl.glVertex2f(hair[i][0], hair[i][1]);
        gl.glEnd();
        // hair 2
        gl.glBegin(GL_POLYGON);
        for (int i = 0 ; i < hair2.length ; i++)
            gl.glVertex2f(hair2[i][0], hair2[i][1]);
        gl.glEnd();

    }
    public static void jaw(GL gl)
    {
    	float[][] jaw = {{-1.6f,3.8f},
                        {-1.3f,3f},
                        {-1.0f,3.5f},
                        {-1.5f,3.9f}
        };
        float[][] jawPoly = {{-1.3f,3f}, //2
                            {-1.1f,2.3f}, //3
                            {-0.6f,3.4f}, //a
                            {-1f,3.6f},//b
                            {-1.0f,3.5f} //8
        };
        float[][] jawPoly2nd = {
                            {-0.6f,3.4f},//a
                            {-1.1f,2.3f},//3
                            {-1.15f,1.85f},//4
                            {-0.7f,1.8f},//5
                            {0f,1.85f}, //6
                            {0f,3.25f} //orgn
        };
        float[][] jawShadow = {
                            {0f,1.85f}, //6
                            {-1.15f,1.85f},//4
                            {-0.8f,1.75f},
                            {-0.25f,1.7555f},
                            {0f,1.8f}
        };
        float[][] jawDetail = {
                            {-1.0f,3.6f}, //6
                            {-1.15f,3.5f},//4
                            {-1.0f,3.5f}
        };
        // Setting the color for the shadow
        gl.glColor3d(217/255f, 164/255f, 127/255f);
        // Draw
        drawSymmetricPoly(jawShadow, gl);
        
        // Setting the color for the jaw
        gl.glColor3d(247/255f, 197/255f, 148/255f);
        // Draw
        drawSymmetricQuad(jaw, gl);
        drawSymmetricPoly(jawPoly, gl);
        drawSymmetricPoly(jawPoly2nd, gl);
        drawSymmetricTriangle(jawDetail, gl);
    }
    public static void mouth(GL gl)
    {
    	float[][] mouth = {{0f,2.9f}, //x
                        {-0.1f,2.9f},
                        {-0.2f,2.8f}, //x
                        {-0.4f,2.85f}, //x
                        {-1.1f,3.25f}, //y
                        {-0.8f,2.2f}, //w
                        {0f,2.2f} //z
        };
        float[][] teeth = {{-1.1f,3.25f}, //1
                        {-0.9f,2.75f}, //2
                        {0f,2.75f}, //3
                        {0f,3.25f},//4
        };
        float[][] teeth2 = {{-0.9f,2.6f}, //1
                        {-0.8f,2.2f}, //2
                        {0f,2.2f}, //3
                        {0f,2.6f},//4
        };
        float[][] mouthDetails = {{0f,2.9f}, //a
                        {-0.3f,2.95f}, //b
                        {-0.6f,3.1f}, //c
                        {-1.1f,3.25f},//d
                        {0f,3.25f}
        };
        // teeth, mouth detail.
        gl.glColor3d(0f,0f,0f);
        // Draw
        drawSymmetricPoly(mouth, gl);
        
        gl.glColor3d(1f, 1f, 1f);
        drawSymmetricQuad(teeth, gl);
        drawSymmetricQuad(teeth2, gl);
        
        gl.glColor3d(247/255f, 197/255f, 148/255f);
        drawSymmetricPoly(mouthDetails, gl);

    }
    public static void nose(GL gl)
    {
    	// Drawing the nose
        numOfPoints = 30;
        radius = 0.25f;
        gl.glColor3d(0f,0f,0f);
        gl.glBegin(GL.GL_POLYGON);
        for (int i = 0; i < numOfPoints; i++) {
            double angle = i * (2.0 * Math.PI / numOfPoints);
            double x = Math.cos(angle) * radius;
            double y = Math.sin(angle) * radius;
            gl.glVertex2d(x ,y + 3.4);
            
        }
        gl.glEnd();
        
        // Nose details
        // White circle
        numOfPoints = 10;
        radius = 0.025f;
        gl.glColor3d(1f,1f,1f);
        gl.glBegin(GL.GL_POLYGON);
        for (int i = 0; i < numOfPoints; i++) {
            double angle = i * (2.0 * Math.PI / numOfPoints);
            double x = Math.cos(angle) * radius * 0.97;
            double y = Math.sin(angle) * radius;
            gl.glVertex2d(x + 0.1 ,y + 3.45);
            
        }
        gl.glEnd();
    }
    public static void eyes(GL gl)
    {
    	float[][] eyes = {{-0.21f,4.45f}, //a
                        {-0.5f,4.61f}, //b
                        {-0.91f,4.3f}, //c
                        {-0.98f,4.01f},//d
                        {-0.6f,3.9f}, //e
                        {-0.13f,3.9f} //f
        };
        // Draw
        gl.glColor3d(1f, 1f, 1f);
        drawSymmetricPoly(eyes, gl);
        
        // Drawing the eyeballs
        // Right side eyes:
        // Green
        numOfPoints = 30;
        radius = 0.2f;
        gl.glColor3d(0f,176/255f,42/455f);
        gl.glBegin(GL.GL_POLYGON);
        for (int i = 0; i < numOfPoints; i++) {
            double angle = i * (2.0 * Math.PI / numOfPoints);
            double x = Math.cos(angle) * radius *.95;
            double y = Math.sin(angle) * radius;
            gl.glVertex2d(x + 0.5 ,y + 4.15);
            
        }
        gl.glEnd();
        
        // Black
        numOfPoints = 30;
        radius = 0.17f;
        gl.glColor3d(0f,0f,0f);
        gl.glBegin(GL.GL_POLYGON);
        for (int i = 0; i < numOfPoints; i++) {
            double angle = i * (2.0 * Math.PI / numOfPoints);
            double x = Math.cos(angle) * radius * 0.97;
            double y = Math.sin(angle) * radius;
            gl.glVertex2d(x + 0.49 ,y + 4.15);
            
        }
        gl.glEnd();
        
        // White
        numOfPoints = 30;
        radius = 0.06f;
        gl.glColor3d(1f,1f,1f);
        gl.glBegin(GL.GL_POLYGON);
        for (int i = 0; i < numOfPoints; i++) {
            double angle = i * (2.0 * Math.PI / numOfPoints);
            double x = Math.cos(angle) * radius * 0.97;
            double y = Math.sin(angle) * radius;
            gl.glVertex2d(x + 0.52 ,y + 4.2);
            
        }
        gl.glEnd();
        
        // Left side eyes:
        // Green
        numOfPoints = 30;
        radius = 0.2f;
        gl.glColor3d(0f,176/255f,42/455f);
        gl.glBegin(GL.GL_POLYGON);
        for (int i = 0; i < numOfPoints; i++) {
            double angle = i * (2.0 * Math.PI / numOfPoints);
            double x = Math.cos(angle) * radius *.95;
            double y = Math.sin(angle) * radius;
            gl.glVertex2d(x - 0.5 ,y + 4.15);
            
        }
        gl.glEnd();
        
        // Black
        numOfPoints = 30;
        radius = 0.17f;
        gl.glColor3d(0f,0f,0f);
        gl.glBegin(GL.GL_POLYGON);
        for (int i = 0; i < numOfPoints; i++) {
            double angle = i * (2.0 * Math.PI / numOfPoints);
            double x = Math.cos(angle) * radius * 0.97;
            double y = Math.sin(angle) * radius;
            gl.glVertex2d(x - 0.49 ,y + 4.15);
            
        }
        gl.glEnd();
        
        // White
        numOfPoints = 30;
        radius = 0.06f;
        gl.glColor3d(1f,1f,1f);
        gl.glBegin(GL.GL_POLYGON);
        for (int i = 0; i < numOfPoints; i++) {
            double angle = i * (2.0 * Math.PI / numOfPoints);
            double x = Math.cos(angle) * radius * 0.97;
            double y = Math.sin(angle) * radius;
            gl.glVertex2d(x - 0.45 ,y + 4.2);
            
        }
        gl.glEnd();
    }
    public static void eyebrows(GL gl)
    {
    	float[][] eyebrows = {{-0.35f,5f}, //a
                        {-1.21f,5f}, //b
                        {-1.39f,4.4f}, //c
                        {-0.5f,4.62f},//d
                        {-0.19f,4.44f} //e
        };
        // Draw
        gl.glColor3d(0f, 0f, 0f);
        drawSymmetricPoly(eyebrows, gl);

    }

    /*
    All the following drawSymmetric_ methods are for drawing the symmetric parts in the character.
    Since crash is symmetric aroud the y axis, all these methods do is take the left half of the drawing,
    multiply the x value by -, and draw on the right side.

    Most of the character's elements were drawn using symmetry.
    */

    public static void drawSymmetricPoly(float[][] array, GL gl)
    {
        // drawing the left side
        gl.glBegin(GL_POLYGON);
        for (int i = 0 ; i < array.length ; i++)
            gl.glVertex2f(array[i][0], array[i][1]);
        gl.glEnd();
        
        // right side
        gl.glBegin(GL_POLYGON);
        for (int i = 0 ; i < array.length ; i++)
            gl.glVertex2f(-array[i][0], array[i][1]);
        gl.glEnd();
    }
    
    public static void drawSymmetricQuad(float[][] array, GL gl)
    {
        // drawing the left side
        gl.glBegin(GL_QUADS);
        for (int i = 0 ; i < array.length ; i++)
            gl.glVertex2f(array[i][0], array[i][1]);
        gl.glEnd();
        
        // right side
        gl.glBegin(GL_POLYGON);
        for (int i = 0 ; i < array.length ; i++)
            gl.glVertex2f(-array[i][0], array[i][1]);
        gl.glEnd();
    }
    
    public static void drawSymmetricQuadStrip(float[][] array, GL gl)
    {
        // drawing the left side
        gl.glBegin(GL_QUAD_STRIP);
        for (int i = 0 ; i < array.length ; i++)
            gl.glVertex2f(array[i][0], array[i][1]);
        gl.glEnd();
        
        // right side
        gl.glBegin(GL_QUAD_STRIP);
        for (int i = 0 ; i < array.length ; i++)
            gl.glVertex2f(-array[i][0], array[i][1]);
        gl.glEnd();
    }
    
    public static void drawSymmetricTriangle(float[][] array, GL gl)
    {
        // drawing the left side
        gl.glBegin(GL.GL_TRIANGLES);
        for (int i = 0 ; i < array.length ; i++)
            gl.glVertex2f(array[i][0], array[i][1]);
        gl.glEnd();
        
        // right side
        gl.glBegin(GL.GL_TRIANGLES);
        for (int i = 0 ; i < array.length ; i++)
            gl.glVertex2f(-array[i][0], array[i][1]);
        gl.glEnd();
    }
    
    public static void drawSymmetricLines(float[][] array, GL gl)
    {
        // drawing the left side
        gl.glBegin(GL.GL_LINES);
        for (int i = 0 ; i < array.length ; i++)
            gl.glVertex2f(array[i][0], array[i][1]);
        gl.glEnd();
        
        // right side
        gl.glBegin(GL.GL_LINES);
        for (int i = 0 ; i < array.length ; i++)
            gl.glVertex2f(-array[i][0], array[i][1]);
        gl.glEnd();
    }
    
}

