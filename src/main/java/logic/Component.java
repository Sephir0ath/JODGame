package main.java.logic;

public abstract class Component
{
	protected GameNode owner;
	
	Component(GameNode owner)
	{
		this.owner = owner;
	}
	
	public final GameNode getOwner()
	{
		return this.owner;
	}
}