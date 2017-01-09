
class Location
{
	public int x,y;

	public Location(){
		x=0; y=0;
	}

	public Location(int x, int y){
		this.x = x;
		this.y = y;
	}

	public Location(Location other){
		this.x = other.x;
		this.y = other.y;
	}

	public boolean equals(Object other){
		if(other instanceof Location){
			Location o = (Location) other;
			return o.x==x && o.y==y;
		}
		return false;
	}

	public int hashCode() {
    	return x*1000+y;
  	}
}
