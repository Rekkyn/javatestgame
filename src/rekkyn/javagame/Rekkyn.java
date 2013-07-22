package rekkyn.javagame;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Rekkyn extends Entity {
    
    public boolean isFree;
    private float dist;
    private int angle;
    public boolean playerControlled;
    boolean motionUp = false, motionDown = false, motionLeft = false, motionRight = false;
    int numberOfKeys = 0;
    float inputX = 0;
    float inputY = 0;
    
    public Rekkyn(float x, float y) {
        super(x, y);
        super.width = 27;
        super.height = 27;
        playerControlled = true;
    }
    
    public Rekkyn(float x, float y, int width, int height) {
        super(x, y);
        super.width = width;
        super.height = height;
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        super.update(container, game, delta);
        
        dist = (float) Math.sqrt((Game.width / 2 - 12 - x) * (Game.width / 2 - 12 - x) + (195 - y) * (195 - y));
        if (!isFree || dist < 10) {
            if (dist != 0) {
                motionX += dist / 20 * (Game.width / 2 - 12 - x) * 0.1;
                motionY += dist / 20 * (195 - y) * 0.1;
            }
            if (Math.abs(motionX) < 0.3 && Math.abs(motionY) < 0.3 && dist < 1) {
                x = Game.width / 2 - 12;
                y = 195;
            }
            if (dist >= 18) {
                isFree = true;
            }
        }
        
        numberOfKeys = 0;
        inputX = 0;
        inputY = 0;
        
        if (playerControlled) {
            input = container.getInput();
            if (input.isKeyDown(Input.KEY_UP)) {
                inputY -= 0.4;
                numberOfKeys++;
            }
            if (input.isKeyDown(Input.KEY_DOWN)) {
                inputY += 0.4;
                numberOfKeys++;
            }
            if (input.isKeyDown(Input.KEY_LEFT)) {
                inputX -= 0.4;
                numberOfKeys++;
            }
            if (input.isKeyDown(Input.KEY_RIGHT)) {
                inputX += 0.4;
                numberOfKeys++;
            }
            
        } else {
            if (world instanceof Play) {
                if (((Play) world).AI) {
                    runAI();
                }
                if (((Play) world).follow) {
                    followMouse();
                }
            }
            // getAngleFromMotion();
        }
        
        if (numberOfKeys >= 2) {
            inputX /= Math.sqrt(2);
            inputY /= Math.sqrt(2);
        }
        motionX += inputX;
        motionY += inputY;
        
        motionX *= 0.95;
        motionY *= 0.95;
        
        float accelX = motionX - prevMotionX;
        int desiredAngle = (int) (accelX / 0.4 * 15);
        if (desiredAngle > angle) angle++;
        if (desiredAngle < angle) angle--;
    }
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.rotate(x + width / 2, y + height / 2, angle);
        Color col = new Color(255, 255, 255);
        if (world instanceof Play) {
            if (((Play) world).disco)
                col = Colour.changeHue(new Color(1.0F, 0.0F, 0.0F), (int) (System.currentTimeMillis() % 1000 * 0.36));
        }
        g.drawImage(Game.scaleImage(Menu.title, 3), x, y, x + width * (1F + 1F / 9F), y + height * (1F + 1F / 9F), 138, 255, 168, 285, col);
        g.rotate(x + 13.5F, y + 13.5F, -angle);
    }
    
    private void followMouse() {
        int mouseX = Mouse.getX();
        int mouseY = Game.height - Mouse.getY();
        double diffX = mouseX - x - width / 2;
        double diffY = mouseY - y - height / 2;
        double angle = Math.atan2(diffY, diffX);
        // this.angle = (int) (angle * 180 / Math.PI) + 135;
        if (diffX * diffX + diffY * diffY > 2) {
            motionX += Math.cos(angle) * 0.4;
            motionY += Math.sin(angle) * 0.4;
        } else {
            x = mouseX - width / 2;
            y = mouseY - height / 2;
        }
    }
    
    public void runAI() {
        if (Game.rand.nextInt(30) == 0) {
            int pickanumberanynumber = Game.rand.nextInt(4);
            if (pickanumberanynumber == 0) {
                motionUp = !motionUp;
                motionDown = false;
            } else if (pickanumberanynumber == 1) {
                motionDown = !motionDown;
                motionUp = false;
            } else if (pickanumberanynumber == 2) {
                motionLeft = !motionLeft;
                motionRight = false;
            } else if (pickanumberanynumber == 3) {
                motionRight = !motionRight;
                motionLeft = false;
            }
        }
        
        if (y < 40) {
            motionUp = false;
            motionDown = true;
        }
        if (y > Game.height - 40) {
            motionDown = false;
            motionUp = true;
        }
        if (x < 40) {
            motionLeft = false;
            motionRight = true;
        }
        if (x > Game.width - 40) {
            motionRight = false;
            motionLeft = true;
        }
        
        if (motionUp) {
            inputY -= 0.4;
            numberOfKeys++;
        }
        if (motionDown) {
            inputY += 0.4;
            numberOfKeys++;
        }
        if (motionLeft) {
            inputX -= 0.4;
            numberOfKeys++;
        }
        if (motionRight) {
            inputX += 0.4;
            numberOfKeys++;
        }
    }
    
    public void getAngleFromMotion() {
        double trigAngle = Math.atan2(motionY, motionX);
        angle = (int) (trigAngle * 180 / Math.PI) + 135;
    }
    
    public void onRightClicked() {
        this.width *= 2;
        this.height *= 2;
        x -= width / 4;
        y -= height / 4;
    }
}
