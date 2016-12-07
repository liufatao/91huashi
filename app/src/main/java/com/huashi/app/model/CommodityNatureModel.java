/**
 * 
 */
package com.huashi.app.model;

import java.util.List;

/**
 * 商品属性型号选择
 * 
 * @author Administrator
 *
 */
public class CommodityNatureModel {
	private int nameLength;// 商品属性名称的长度

	/**
	 * message : 查询成功
	 * status : 1
	 * commodityTypeModel : {"id":144,"picture":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","name":"大疆养生玉枣特级装600g","commodityType":[{"modelId":15,"typeName":"大疆养生大枣特级装480g","price":"85.00","true_sell":"85.00"},{"modelId":16,"typeName":"大疆养生玉枣精选装420g1","price":"148.00","true_sell":"148.00"},{"modelId":17,"typeName":"大疆养生玉枣特级装600g","price":"148.00","true_sell":"148.00"},{"modelId":18,"typeName":"大疆养生玉枣优选装960g","price":"128.00","true_sell":"128.00"}]}
	 */

	private String message;
	private int status;
	/**
	 * id : 144
	 * picture : 7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b
	 * name : 大疆养生玉枣特级装600g
	 * commodityType : [{"modelId":15,"typeName":"大疆养生大枣特级装480g","price":"85.00","true_sell":"85.00"},{"modelId":16,"typeName":"大疆养生玉枣精选装420g1","price":"148.00","true_sell":"148.00"},{"modelId":17,"typeName":"大疆养生玉枣特级装600g","price":"148.00","true_sell":"148.00"},{"modelId":18,"typeName":"大疆养生玉枣优选装960g","price":"128.00","true_sell":"128.00"}]
	 */

	private CommodityTypeModelBean commodityTypeModel;

	public int getNameLength() {
		return nameLength;
	}

	public void setNameLength(int nameLength) {
		this.nameLength = nameLength;
	}



	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public CommodityTypeModelBean getCommodityTypeModel() {
		return commodityTypeModel;
	}

	public void setCommodityTypeModel(CommodityTypeModelBean commodityTypeModel) {
		this.commodityTypeModel = commodityTypeModel;
	}


	public static class CommodityTypeModelBean {
		private int id;
		private String picture;
		private String name;
		/**
		 * modelId : 15
		 * typeName : 大疆养生大枣特级装480g
		 * price : 85.00
		 * true_sell : 85.00
		 */

		private List<CommodityTypeBean> commodityType;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getPicture() {
			return picture;
		}

		public void setPicture(String picture) {
			this.picture = picture;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<CommodityTypeBean> getCommodityType() {
			return commodityType;
		}

		public void setCommodityType(List<CommodityTypeBean> commodityType) {
			this.commodityType = commodityType;
		}

		public static class CommodityTypeBean {
			private int modelId;
			private String typeName;
			private String price;
			private String true_sell;
			private boolean nameIsSelect;// 商品属性是否选中
			public boolean getNameIsSelect() {
				return nameIsSelect;
			}

			public void setNameIsSelect(boolean nameIsSelect) {
				this.nameIsSelect = nameIsSelect;
			}
			public int getModelId() {
				return modelId;
			}

			public void setModelId(int modelId) {
				this.modelId = modelId;
			}

			public String getTypeName() {
				return typeName;
			}

			public void setTypeName(String typeName) {
				this.typeName = typeName;
			}

			public String getPrice() {
				return price;
			}

			public void setPrice(String price) {
				this.price = price;
			}

			public String getTrue_sell() {
				return true_sell;
			}

			public void setTrue_sell(String true_sell) {
				this.true_sell = true_sell;
			}
		}
	}
}
