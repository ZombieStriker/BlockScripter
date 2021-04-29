package me.zombie_striker.blockscripter.easygui.examples;

import me.zombie_striker.blockscripter.easygui.ClickData;
import me.zombie_striker.blockscripter.easygui.EasyGUICallable;

public class HelloWorldCallable extends EasyGUICallable {

	public void call(ClickData data) {
		data.getClicker().sendMessage("Hello World!");
	}
}
