package com.mona.game.windowHandler;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

/**
 * Created by _Silence on 2016/1/4.
 */
public class bbContactListener implements ContactListener {

    private int numFootContacts;
    private Array<Body> bodiesToRemove;
    private boolean playerDead;
    private boolean gesture;
    public bbContactListener() {
        super();
        bodiesToRemove = new Array<Body>();
        gesture = true;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();


        if(fa == null || fb == null) return;

        if(fa.getUserData() != null && fa.getUserData().equals("foot")) {
            numFootContacts++;
            gesture = false;
        }
        if(fb.getUserData() != null && fb.getUserData().equals("foot")) {
            numFootContacts++;
            gesture = false;
        }

        if(fa.getUserData() != null && fa.getUserData().equals("enemy")) {
            bodiesToRemove.add(fa.getBody());
        }
        if(fb.getUserData() != null && fb.getUserData().equals("enemy")) {
            bodiesToRemove.add(fb.getBody());
        }

        if(fa.getUserData() != null && fa.getUserData().equals("spike")) {
            playerDead = true;
        }
        if(fb.getUserData() != null && fb.getUserData().equals("spike")) {
            playerDead = true;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(fa == null || fb == null) return;

        if(fa.getUserData() != null && fa.getUserData().equals("foot")) {
            numFootContacts--;
        }
        if(fb.getUserData() != null && fb.getUserData().equals("foot")) {
            numFootContacts--;
        }

    }

    public Array<Body> getBodies() { return bodiesToRemove; }
    public boolean isPlayerDead() { return playerDead; }

    public void preSolve(Contact c, Manifold m) {}
    public void postSolve(Contact c, ContactImpulse ci) {}
    public  boolean getGesture(){
        return gesture;
    }
    public void setGesture(){
        gesture = true;
    }
}