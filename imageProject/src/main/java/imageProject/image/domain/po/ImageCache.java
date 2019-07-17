package imageProject.image.domain.po;

import java.time.LocalDateTime;

public class ImageCache {
	private Integer imageId;

	private String imageUrl;

	private String imageName;

	private Long articleId;

	private String remark;

	private Boolean isDownload;

	private LocalDateTime createTime;

	private LocalDateTime downloadTime;

	private String md5Mark;

	public Integer getImageId() {
		return imageId;
	}

	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl == null ? null : imageUrl.trim();
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName == null ? null : imageName.trim();
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Boolean getIsDownload() {
		return isDownload;
	}

	public void setIsDownload(Boolean isDownload) {
		this.isDownload = isDownload;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getDownloadTime() {
		return downloadTime;
	}

	public void setDownloadTime(LocalDateTime downloadTime) {
		this.downloadTime = downloadTime;
	}

	public String getMd5Mark() {
		return md5Mark;
	}

	public void setMd5Mark(String md5Mark) {
		this.md5Mark = md5Mark == null ? null : md5Mark.trim();
	}

	@Override
	public String toString() {
		return "ImageCache [imageId=" + imageId + ", imageUrl=" + imageUrl + ", imageName=" + imageName + ", articleId="
				+ articleId + ", remark=" + remark + ", isDownload=" + isDownload + ", createTime=" + createTime
				+ ", downloadTime=" + downloadTime + ", md5Mark=" + md5Mark + "]";
	}

}