package PA3;

import java.awt.Color;
import java.util.ArrayList;

public class ImageProcessor {

	public Picture picture;
	public int height, width;
	public int[][] yImportance;
	public int[][] xImportance;
	public int[][] I;
	public Color[][] picMatrix;
	
	public ImageProcessor(String imageFile){
		this.picture = new Picture(imageFile);
		this.height = picture.height();
		this.width = picture.width();
		//set up picture matrix and the importance matrices allowing us to re-size the image
		picMatrix = new Color[this.height][this.width];
		picMatrix(); // Set up the pixel matrix
		
		
	}
	
	private void picMatrix(){
		
		for(int i = 0; i < this.height; i++){
			for(int j = 0; j < this.width; j++){
				this.picMatrix[i][j] = this.picture.get(j,i);
			}
		}
	}
	
	private void yImportance(){ 
		
		this.yImportance = new int[this.picMatrix.length][this.picMatrix[0].length];
		
/*		System.out.println("Current Height: " + this.picMatrix.length);
		System.out.println("Current  Width: " + this.picMatrix[0].length);*/
		
		for(int i = 0; i < this.picMatrix.length; i++){
			for(int j  = 0; j < this.picMatrix[0].length; j++){
				if(i == 0){
					this.yImportance[i][j] = Dist(this.picMatrix[this.picMatrix.length - 1][j], this.picMatrix[i+1][j]);
				} else if(i == (this.picMatrix.length - 1)){
					this.yImportance[i][j] = Dist(this.picMatrix[i-1][j], this.picMatrix[0][j]);
				}else {
					this.yImportance[i][j] = Dist(this.picMatrix[i-1][j], this.picMatrix[i+1][j]);
				}
			}
		}
	}
	
	private void xImportance(){
		
		this.xImportance = new int[this.picMatrix.length][this.picMatrix[0].length];
		
/*		System.out.println("Current Height: " + this.picMatrix.length);
		System.out.println("Current  Width: " + this.picMatrix[0].length);*/
		
		for(int i = 0; i < this.picMatrix.length; i++){
			for(int j  = 0; j < this.picMatrix[0].length; j++){
				if(j == 0){
					this.xImportance[i][j] = Dist(this.picMatrix[i][this.picMatrix[0].length-1], this.picMatrix[i][j+1]);
				} else if(j == (this.picMatrix[0].length -1)){ 
					this.xImportance[i][j] = Dist(this.picMatrix[i][0], this.picMatrix[i][j-1]);
				} else {
					this.xImportance[i][j] = Dist(this.picMatrix[i][j-1], this.picMatrix[i][j+1]);
				}
			}
		}
	}


	private void importance(){
		
		this.I = new int[this.picMatrix.length][this.picMatrix[0].length];
		
/*		System.out.println("Current Height: " + this.picMatrix.length);
		System.out.println("Current  Width: " + this.picMatrix[0].length);*/
		
		for(int i = 0; i < this.I.length; i++){
			for(int j  = 0; j < this.I[0].length; j++){
				this.I[i][j] = xImportance[i][j] + yImportance[i][j];
			}
		}
	}
	
	//Get the distance between the colors of the two pixels
	private int Dist(Color p, Color q){
		int red = (int) Math.pow((p.getRed() - q.getRed()), 2);
		int green = (int) Math.pow((p.getGreen() - q.getGreen()), 2);
		int blue = (int) Math.pow((p.getBlue() - q.getBlue()), 2);
		
		return red + green + blue;
	}
	
	private void modifyPicMatrix(ArrayList<Integer> cut){
		
		Color[][] newPic = new Color[this.picMatrix.length][this.picMatrix[0].length - 1];
		for(int i = 0; i < newPic.length; i++){
			for(int j = 0; j < newPic[0].length; j++){
				if(j < cut.get(i+1)){
					newPic[i][j] = this.picMatrix[i][j];
				} else if(j >= cut.get(i+1)){
					newPic[i][j] = this.picMatrix[i][j+1];
				}
			}
		}
		
		this.picMatrix = newPic;
	}
	
	private Picture setReducedPicture(Color[][] reducedPic){
		Picture newPic = new Picture(reducedPic[0].length, reducedPic.length);
		for(int i = 0; i < reducedPic.length; i++){
			for(int j = 0; j < reducedPic[0].length; j++){
				newPic.set(j, i, reducedPic[i][j]);
			}
		}
		
		
		return newPic;
	}
	
	public Picture reduceWidth(double x){
		int pixelReduction = (int) Math.ceil(x * (double) this.width);
		System.out.println(pixelReduction);
		ArrayList<Integer> minCut = new ArrayList<Integer>();
		
		// Find the minVC to reduce the image size
		// Do this however many times is needed to reduce
		// the image by the desired pixel amount.
		for(int i = 0; i < pixelReduction; i++){
			yImportance(); // Set up the x importance matrix
			xImportance(); // Set up the y importance matrix
			importance(); // Set up the overall pixel importance matrix
			
			minCut = DynamicProgramming.minCostVC(this.I);
			modifyPicMatrix(minCut);
		}
		
		Picture reducedImage = setReducedPicture(this.picMatrix);
		
		return reducedImage;
	}
	
	
	public static void main(String args[]){
		// Create image processor object
		// Pass in image.
		ImageProcessor image = new ImageProcessor("/home/btheisen/workspace/PA3/PA3/resize.jpg");
		// Show image
		image.picture.show();
		//Call reduce image
		Picture reduced = image.reduceWidth(0.000375);
		// Show image
		reduced.show();
	}
}
