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
		picMatrix(); // Set up the pixel matrix
		yImportance(); // Set up the x importance matrix
		xImportance(); // Set up the y importance matrix
		importance(); // Set up the overall pixel importance matrix
		
	}
	
	private void picMatrix(){
		for(int i = 0; i < this.height; i++){
			for(int j = 0; j < this.width; j++){
				this.picMatrix[i][j] = this.picture.get(j,i);
			}
		}
	}
	
	private void yImportance(){ 
		for(int i = 0; i < this.height; i++){
			for(int j  = 0; j < this.width; j++){
				if(i == 0){
					this.yImportance[i][j] = Dist(this.picMatrix[this.height-1][j], this.picMatrix[i+1][j]);
				} else if(i == (this.height - 1)){
					this.yImportance[i][j] = Dist(this.picMatrix[i-1][j], this.picMatrix[0][j]);
				}else {
					this.yImportance[i][j] = Dist(this.picMatrix[i-1][j], this.picMatrix[i+1][j]);
				}
			}
		}
	}
	
	private void xImportance(){
		for(int i = 0; i < this.height; i++){
			for(int j  = 0; j < this.width; j++){
				if(j == 0){
					this.xImportance[i][j] = Dist(this.picMatrix[i][this.width-1], this.picMatrix[i][j+1]);
				} else if(j == (this.width -1)){ 
					this.xImportance[i][j] = Dist(this.picMatrix[i][0], this.picMatrix[i][j-1]);
				} else {
					this.xImportance[i][j] = Dist(this.picMatrix[i][j-1], this.picMatrix[i][j+1]);
				}
			}
		}
	}


	private void importance(){
		for(int i = 0; i < this.height; i++){
			for(int j  = 0; j < this.width; j++){
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
	
	private void modifyI(ArrayList<Integer> cut){
		int[][] newI = new int[this.I.length -1][this.I[0].length - 1];
		for(int i = 0; i < newI.length; i++){
			for(int j = 0; j < newI[0].length; j++){
				if(j < cut.get(i+1)){
					newI[i][j] = this.I[i][j];
				} else if(j >= cut.get(i+1)){
					newI[i][j] = this.I[i][j+1];
				}
			}
			
		}
		
		this.I = newI;
	}
	
	private Picture setReducedPicture(int[][] reducedPic){
		Picture newPic = new Picture(reducedPic[0].length, reducedPic.length);
		for(int i = 0; i < reducedPic.length; i++){
			for(int j = 0; j < reducedPic[0].length; j++){
				
			}
		}
		
		
		return newPic;
	}
	
	public Picture reduceWidth(double x){
		int pixelReduction = (int) Math.ceil(x * (double) this.width);
		ArrayList<Integer> minCut = new ArrayList<Integer>();
		
		// Find the minVC to reduce the image size
		// Do this 
		for(int i = 0; i < pixelReduction; i++){
			minCut = DynamicProgramming.minCostVC(this.I);
			modifyI(minCut);
		}
		
		Picture reducedImage = setReducedPicture(this.I);
		
		return reducedImage;
	}
	
}
