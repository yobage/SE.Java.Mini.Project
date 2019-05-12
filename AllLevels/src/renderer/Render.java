package renderer;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
//import elements.LightSource;
//import geometries.FlatGeometry;
import geometries.Geometry;
import primitives.Point3D;
import primitives.Ray;
//import primitives.Vector;
import scene.Scene;

public class Render {
	
	private Scene _scene;
	private ImageWriter _imagewriter;
	//private final int RECURSION_LEVEL = 3;
	
	// ***************** Constructors ********************** //
	/**
	 * default constructor, setting name to "default", width=height=nx=ny=500
	 */
	public Render() {
		super();
		this._scene = new Scene();
		this._imagewriter = new ImageWriter("Default",500,500,500,500);
	}
//Constructor get Scene and ImageWriter
	public Render(ImageWriter imagewriter, Scene scene) {
		super();
		this._scene = new Scene(scene);
		this._imagewriter = imagewriter;
	}
	/**
	 * renderImage - A function whose function is to produce the actual image
	 * @throws Exception 
	 */
	// ***************** Operations ******************** //
	public void renderImage() throws Exception
	{
		for (int i=0;i<_imagewriter.getHeight();i++){
            for (int j=0;j<_imagewriter.getWidth();j++)
            {
        		Ray ray = _scene.get_camera().constructRayThroughPixel(_imagewriter.getNx(), _imagewriter.getNy(),i,j, _scene.get_screenDistance(), _imagewriter.getWidth(),_imagewriter.getHeight());
        		List<Point3D> intersectionPoints = getSceneRayIntersections(ray);
        		if (intersectionPoints.isEmpty())
        			_imagewriter.writePixel(i, j, _scene.get_background());
        		else
        		{
        		Point3D closestPoint = getClosestPoint(intersectionPoints);
        		_imagewriter.writePixel(i, j, calcColor(closestPoint));
        		}
            }
         }	
	}
	/**
	 * printGrid
	 * @param interval
	 */
	public void printGrid(int interval){
		for (int i=0;i<_imagewriter.getHeight();i++)
            for (int j=0;j<_imagewriter.getWidth();j++)
            {
                if(i%interval==0 || j%interval==0 )
                	_imagewriter.writePixel(j,i,Color.WHITE); 
            }   
	}
	/**
	 * get_scene
	 * @return
	 */
	public Scene get_scene() {
		return _scene;
	}
	/**
	 * set_scene
	 * @param _scene
	 */
	public void set_scene(Scene _scene) {
		this._scene = _scene;
	}
	/**
	 * get_imagewriter
	 * @return
	 */
	public ImageWriter get_imagewriter() {
		return _imagewriter;
	}
	/**
	 * set_imagewriter
	 * @param _imagewriter
	 */
	public void set_imagewriter(ImageWriter _imagewriter) {
		this._imagewriter = _imagewriter;
	}
	
	@Override
	public String toString() {
		return "Render [_scene=" + _scene + ", _imagewriter=" + _imagewriter + "]";
	}


	/**
	 * getSceneRayIntersections 
	 * @param ray
	 * @return
	 * @throws Exception 
	 */
	private List<Point3D> getSceneRayIntersections(Ray ray) throws Exception
	{
		Iterator<Geometry> geometries = _scene.getGeometriesIterator();
		List<Point3D> intersectionPoints = new ArrayList<Point3D>();
		while (geometries.hasNext()){
			Geometry geometry = geometries.next();
			List<Point3D> geometryIntersectionPoints = geometry.findIntersection(ray);
			intersectionPoints.addAll(geometryIntersectionPoints);
		}
		return intersectionPoints;
		
	}
	/**
	 * getClosestPoint - A function that receives a list of points and finds the nearest point
	 * @param intersectionPoints
	 * @return
	 * @throws Exception 
	 */
	private Point3D getClosestPoint(List<Point3D> intersectionPoints) throws Exception
	{
		double distance = Double.MAX_VALUE;
		Point3D P0 = _scene.get_camera().getP0();
		Point3D minDistancePoint = null;
		for (Point3D point: intersectionPoints)
			{if (P0.distance(point) < distance)
			{
			minDistancePoint = new Point3D(point);
			distance = P0.distance(point);
			}
		}
		return minDistancePoint;
	}
	public Color calcColor(Point3D p){
		return _scene.get_ambientLight().getIntensity(p);
	}

}
