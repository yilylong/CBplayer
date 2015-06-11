package com.chongbao.cbplayer.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.chongbao.cbplayer.R;
/**
 * 自定义的progressBar继承一个surfaceView提高绘图效率，当然也会比view耗性能
 * @author zhl
 *
 */
public class CBProgressBar extends CBbaseView {
	private static final int STYLE_HORIZONTAL=0;
	private static final int STYLE_ROUND=1;
	/**进度背景画笔**/
//	private Paint mBgpaint;
	/**进度画笔**/
//	private Paint mPrgpaint;
	/**进度文字画笔**/
//	private Paint mTextpaint;
	/**圆形进度条边框宽度**/
	private int strokeWidth;
	/**进度条中心X坐标**/
	private int centerX;
	/**进度条中心Y坐标**/
	private int centerY;
	/**进度提示文字大小**/
	private int percenttextsize=0;
	/**进度提示文字颜色**/
	private int percenttextcolor=0;
	/**进度条背景颜色**/
	private int progressBarBgColor=0;
	/**进度颜色**/
	private int progressColor=0;
	/**进度条样式（水平/圆形）**/
	private int orientation=0;
	/**圆形进度条半径**/
	private int radius = 0;
	/**进度最大值**/
	private int max = 100;
	/**进度值**/
	private int progress = 0;
	/**水平进度条是否是空心**/
	private boolean isHorizonStroke;
	/**进度文字是否显示百分号**/
	private boolean showPercentSign;
	/**水平进度圆角值**/
	private int rectRound;
	
	public CBProgressBar(Context context){
		this(context, null);
	}
	public CBProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	public CBProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.cbprogressbar);
		percenttextcolor = array.getColor(R.styleable.cbprogressbar_percent_text_color, getResources().getColor(R.color.percent_text_color_default));
		progressBarBgColor = array.getColor(R.styleable.cbprogressbar_progressBarBgColor, getResources().getColor(R.color.progressbar_bg_color_default));
		progressColor = array.getColor(R.styleable.cbprogressbar_progressColor, getResources().getColor(R.color.progress_color_default));
		percenttextsize = (int) array.getDimension(R.styleable.cbprogressbar_percent_text_size, getResources().getDimension(R.dimen.percent_text_size_default));
		strokeWidth = (int) array.getDimension(R.styleable.cbprogressbar_stroke_width, getResources().getDimension(R.dimen.stroke_width_default));
		rectRound = (int) array.getDimension(R.styleable.cbprogressbar_rect_round, getResources().getDimension(R.dimen.rect_round_default));
		orientation = array.getInteger(R.styleable.cbprogressbar_orientation, 0);
		isHorizonStroke = array.getBoolean(R.styleable.cbprogressbar_isHorizonStroke, false);
		showPercentSign = array.getBoolean(R.styleable.cbprogressbar_showPercentSign, true);
//		mBgpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//		mPrgpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//		mTextpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		array.recycle();
	}
	

	@Override
	protected void render(Canvas canvas, Paint piant) {
		centerX = getWidth()/2;
		centerY = getHeight()/2;
		radius = centerX-strokeWidth/2;
		if(orientation==STYLE_HORIZONTAL){
			drawHoriRectProgressBar(canvas,piant);
		}else{
			drawRoundProgressBar(canvas,piant);
		}
	}
	
	

	/**
	 * 绘制圆形进度条
	 * @param canvas
	 */
	private void drawRoundProgressBar(Canvas canvas,Paint piant){
		// 初始化画笔属性
		piant.setColor(progressBarBgColor);
		piant.setStyle(Paint.Style.STROKE);
		piant.setStrokeWidth(strokeWidth);
		// 画圆
		canvas.drawCircle(centerX, centerY, radius, piant);
		// 画圆形进度
		piant.setColor(progressColor);
		piant.setStyle(Paint.Style.STROKE);
		piant.setStrokeWidth(strokeWidth);
		RectF oval = new RectF(centerX - radius, centerY - radius, radius+ centerX, radius + centerY);
		canvas.drawArc(oval, -90, 360*progress/max, false, piant);
		// 画进度文字
		piant.setStyle(Paint.Style.FILL);
		piant.setColor(percenttextcolor);
		piant.setTextSize(percenttextsize);
		
		String percent = (int)(progress*100/max)+"";
		if(showPercentSign){
			percent+="%";
		}
		Rect rect = new Rect();
		piant.getTextBounds(percent, 0, percent.length(), rect);
		float textWidth = rect.width();
		float textHeight = rect.height();
		if(textWidth>=radius*2){
			textWidth = radius*2;
		}
		canvas.drawText(percent, centerX-textWidth/2, centerY+textHeight/2, piant);
		
	}
	/**
	 * 绘制水平矩形进度条
	 * @param canvas
	 */
	private void drawHoriRectProgressBar(Canvas canvas,Paint piant){
		// 初始化画笔属性
		piant.setColor(progressBarBgColor);
		if(isHorizonStroke){
			piant.setStyle(Paint.Style.STROKE);
			piant.setStrokeWidth(strokeWidth);
		}else{
			piant.setStyle(Paint.Style.FILL);
		}
		// 画水平矩形
		canvas.drawRoundRect(new RectF(centerX - getWidth()/2, centerY - getHeight()/2,
				centerX+getWidth()/2, centerY + getHeight()/2), rectRound, rectRound, piant);

		// 画水平进度
		piant.setStyle(Paint.Style.FILL);
		piant.setColor(progressColor);
		canvas.drawRoundRect(new RectF(centerX - getWidth()/2, centerY - getHeight()/2,
				((progress*100/max)*getWidth())/100, centerY + getHeight()/2), rectRound, rectRound, piant);

		// 画进度文字
		piant.setStyle(Paint.Style.FILL);
		piant.setColor(percenttextcolor);
		piant.setTextSize(percenttextsize);
		String percent = (int)(progress*100/max)+"";
		if(showPercentSign){
			percent+="%";
		}
		Rect rect = new Rect();
		piant.getTextBounds(percent, 0, percent.length(), rect);
		float textWidth = rect.width();
		float textHeight = rect.height();
		if(textWidth>=getWidth()){
			textWidth = getWidth();
		}
		canvas.drawText(percent, centerX-textWidth/2, centerY+textHeight/2, piant);
				
	}
	
	public void setProgress(int progress){
		if(progress>max){
			progress = max;
		}else{
			this.progress = progress;
//			postInvalidate();
		}
	}
	
	public void setMax(int max){
		this.max=max;
	}
	public int getStrokeWidth() {
		return strokeWidth;
	}
	public void setStrokeWidth(int strokeWidth) {
		this.strokeWidth = strokeWidth;
	}
	public int getPercenttextsize() {
		return percenttextsize;
	}
	public void setPercenttextsize(int percenttextsize) {
		this.percenttextsize = percenttextsize;
	}
	public int getPercenttextcolor() {
		return percenttextcolor;
	}
	public void setPercenttextcolor(int percenttextcolor) {
		this.percenttextcolor = percenttextcolor;
	}
	public int getProgressBarBgColor() {
		return progressBarBgColor;
	}
	public void setProgressBarBgColor(int progressBarBgColor) {
		this.progressBarBgColor = progressBarBgColor;
	}
	public int getProgressColor() {
		return progressColor;
	}
	public void setProgressColor(int progressColor) {
		this.progressColor = progressColor;
	}
	public int getOrientation() {
		return orientation;
	}
	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}
	public boolean isHorizonStroke() {
		return isHorizonStroke;
	}
	public void setHorizonStroke(boolean isHorizonStroke) {
		this.isHorizonStroke = isHorizonStroke;
	}
	public int getRectRound() {
		return rectRound;
	}
	public void setRectRound(int rectRound) {
		this.rectRound = rectRound;
	}
	public int getMax() {
		return max;
	}
	public int getProgress() {
		return progress;
	}
	
	
}
