package APS.Mouse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class mouseListener implements MouseListener,MouseMotionListener {

    Color corOriginal;
    Color corHover = new Color(100,70,180);

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JButton botao = (JButton) e.getSource();
        corOriginal = botao.getBackground();
        botao.setBackground(corHover);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JButton botao = (JButton) e.getSource();
        botao.setBackground(corOriginal);
    }

    //MouseMotionListener

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

}

