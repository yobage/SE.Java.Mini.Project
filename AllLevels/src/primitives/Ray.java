package primitives;

public class Ray
{
	private Point3D _POO;
	// direction
	private Vector _direction;

	// ***************** Constructors ********************** //
	//Default Constructor
	public Ray()
	{
		_POO = new Point3D();
		_direction = new Vector();
	}
	//Copy constructor
	public Ray(Ray ray)
	{
		this._POO = ray.getPOO();
		this._direction = ray.getDirection();
	}

	public Ray(Point3D point, Vector direction) throws Exception
	{
		this._POO = new Point3D(point);
		this._direction = new Vector (direction).normalize();
	}

	// ***************** Getters/Setters ********************** //
	public void setPOO(Point3D pnt) { this._POO = new Point3D(pnt); }
	public void setDirection(Vector _direction) throws Exception
	{ this._direction = new Vector(_direction).normalize();  }
	public Vector getDirection() { return new Vector(_direction); }
	public Point3D getPOO()        { return new Point3D(_POO); }
	
	@Override
	public String toString() {
		return "Ray [point=" + _POO + ", direction=" + _direction + "]";
	}
}