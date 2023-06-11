package kp.ui;

import kp.input.*;

public class SimpleButton
{
    private int id;
    private ButtonActionEvent event;
    public boolean state;
    public int x;
    public int y;
    public int width;
    public int height;
    public String text;
    public boolean useState;
    public boolean hover;
    private static IButtonRenderer renderer;
    
    static {
        SimpleButton.renderer = new IButtonRenderer() {
            @Override
            public void render(final SimpleButton button) {
            }
        };
    }
    
    public SimpleButton(final int id, final String text, final int x, final int y, final int w, final int h) {
        this.state = true;
        this.useState = false;
        this.hover = false;
        this.id = id;
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }
    
    public void setActionEvent(final ButtonActionEvent e) {
        this.event = e;
    }
    
    public void draw() {
        SimpleButton.renderer.render(this);
    }
    
    public void mouseInput(final int x, final int y, final int ev) {
        if (ev == -1) {
            if (this.isInside(x, y)) {
                this.hover = true;
            }
            else {
                this.hover = false;
            }
        }
        else if (this.isInside(x, y) && InputMethod.getMinecraftInterface().getEventButtonState() && this.event != null) {
            this.state = !this.state;
            this.event.onClick(this.id);
        }
    }
    
    public boolean isInside(final int x, final int y) {
        return x > this.x && x < this.x + this.width && y > this.y && y < this.y + this.height;
    }
    
    public void setPosition(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
    
    public static void setRenderInterface(final IButtonRenderer e) {
        SimpleButton.renderer = e;
    }
    
    public interface ButtonActionEvent
    {
        void onClick(final int p0);
    }
    
    public interface IButtonRenderer
    {
        void render(final SimpleButton p0);
    }
}
