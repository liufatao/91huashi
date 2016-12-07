package com.huashi.app.api;

/**
 * Created by Administrator on 2016/6/6.
 */
public class RequestUrlsConfig {
    private static final String BASE_SERVER=HuashiApi.BASEAPI;
    //主页url
    public static final String HOME_URL=BASE_SERVER.concat("/index/queryIndexData.do");
    //商品详情url
    public static final String COMMODITY_URL=BASE_SERVER.concat("/commodity/queryViewData.do");
    //获取验证码
    public static final String VERIFYCODE_URL=BASE_SERVER.concat("/account/verifycode.do");
    //注册
    public static final String REGISTER_URL=BASE_SERVER.concat("/account/regist.do");
    //个人中心
    public static final String PERSONAL_URL=BASE_SERVER.concat("/account/getUserInfo.do");
    //登录
    public static final String LOGO_URL=BASE_SERVER.concat("/account/login.do");
    //忘记密码
    public static  final String FINDPASSWORDCHECK_URL=BASE_SERVER.concat("/account/findpwdCheck.do");
    //找回密码
    public static final String FINDPASSWORD_URL=BASE_SERVER.concat("/account/findpwd.do");
    //替换头像
    public static final String UPDATEAVATAR=BASE_SERVER.concat("/user/updateAvatar.do");
    //修改个性说明单缸、风冷、四冲程
    public static final String UPDATEMOTTO=BASE_SERVER.concat("/user/updateMotto.do");
    //账号资料修改页面数据查询
    public static final String QUERYUSERBYID=BASE_SERVER.concat("/user/queryUserById.do");
    //修改密码
    public static final String UPDDTEPASSWORD=BASE_SERVER.concat("/user/updatePassword.do");
    //分类
    public static final String INDUSTRYQUERY=BASE_SERVER.concat("/search/industryQuery.do");
    //分类商品
    public static final String INDUSTRYCIASAIYQUERY=BASE_SERVER.concat("/search/industryClassifyQuery.do");
    //商品评论
    public static final String QUERYCOMMENTS=BASE_SERVER.concat("/commodity/queryComments.do");
    //加入购物车
    public static final String ADDCOMMODITYTOCART=BASE_SERVER.concat("/shoppingcart/addCommodityToCart.do");
    //用户添加地址
    public static final String INSERTUSERADDRESS=BASE_SERVER.concat("/user/insertUserAddress.do");
    //用户修改地址
    public static final String UPDATEUSERADDRESS=BASE_SERVER.concat("/user/updateUserAddress.do");
    //用户删除地址
    public static final String DELETEUSERADDRESS=BASE_SERVER.concat("/user/deleteUserAddress.do");
    //查询用户地址
    public static final String QUERYUSERADDRESSES=BASE_SERVER.concat("/user/queryUserAddresses.do");
    //查询单个用户地址
    public static final String QUERYUSERADDRESS=BASE_SERVER.concat("/user/queryUserAddress.do");
    //加入购物车前的数据查询
    public static final String QUERYCOMMODITYTYPES=BASE_SERVER.concat("/commodity/queryCommodityTypes.do");
    //立即购买
    public static final String INSERTORDER=BASE_SERVER.concat("/order/insertOrder.do");
    //提交订单
    public static final String INSERTORDERDETSIL=BASE_SERVER.concat("/order/insertOrderDetail.do");
    //确认订单查询
    public static final String ORDERQUERORDERDETAILBYUSERID=BASE_SERVER.concat("/order/queryOrderDetailById.do");
    //查询购物车数据
    public static final String QUERYCARTDETAILS=BASE_SERVER.concat("/shoppingcart/queryCartDetails.do");
    //删除购物车商品
    public static final String DELETESHOPPINGCART=BASE_SERVER.concat("/shoppingcart/deleteShoppingCart.do");
    //加入收藏
    public static final String INSERTCOLLECTCOMMODITY=BASE_SERVER.concat("/commodity/insertCollectCommodity.do");
    //取消收藏
    public static final String CANCELCOLLECTCOMMODITY=BASE_SERVER.concat("/commodity/cancelCollectCommodity.do");
    //修改购物车商品数量
    public static final String UPDATESHOPINGCARTDETAILS=BASE_SERVER.concat("/shoppingcart/updateShoppingCartDetails.do");
    //查询所有收藏
    public static  final String QUERYUSERCOLLECTCOMMOSIRYS=BASE_SERVER.concat("/commodity/queryUserCollectCommoditys.do");
    //查询我的评论
    public static  final String QUERYUSERCOMMENTS=BASE_SERVER.concat("/commodity/queryUserComments.do");
    //删除我的评论
    public static final String DELETECOMMODITYCOMMENT=BASE_SERVER.concat("/commodity/deleteCommodityComment.do");
    //意见反馈
    public static final String INSERTFEEDBACK=BASE_SERVER.concat("/system/insertFeedback.do");
    //版本更新
//    public static final String UPGRADEANDROIDAPPVERSION=BASE_SERVER.concat("/system/upgradeAndroidAppVersion.do");
    //购物车数据提交
    public static final String SHOPPINGCARTOORDER=BASE_SERVER.concat("/order/shoppingcarToOrder.do");
    //批量删除购物车商品
    public static final String DELETESHOPPINGCARTBYID=BASE_SERVER.concat("/shoppingcart/deleteShoppingCartById.do");
    //订单详情
    public static final String SHOPPINGCARTOORDERDETSILS=BASE_SERVER.concat("/order/shoppingcarToOrderDetails.do");
    //查询购物车订单信息
    public static final String QUERYCOMMODITYINFOBYID=BASE_SERVER.concat("/order/queryCommodityInfoById.do");
    //修改配送方式支付方式
    public static final String UPDATECOSTTYPE=BASE_SERVER.concat("/order/updateCostType.do");
    //支付界面查询信息
    public static final String GETPRIVATEKEY=BASE_SERVER.concat("/alipay/getPrivateKey.do");
    //支付宝服务器异步通知地址
    public static final String RECEIVENOTIFY=BASE_SERVER.concat("/alipay/receiveNotify.do");
    //微信支付服务器异步通知地址
    public static final String WEIXINRECEIVENOTIFY=BASE_SERVER.concat("/weixinpay/receiveNotify.do");
    //推荐产品
    public static final String QUERYREXOMMWNSCOMMODITIES=BASE_SERVER.concat("/order/queryRecommendCommodities.do");
    //搜索接口
    public static final String SEARCHCOMMODITYDATA=BASE_SERVER.concat("/search/searchCommoditydata.do");
    //查询购物车数量
    public static final String QUERYSHOPINGCARTSNUM=BASE_SERVER.concat("/shoppingcart/queryShoppingCartsNum.do");
    //查询待付款订单
    public static final String QUERYTOPAYORDERS=BASE_SERVER.concat("/order/queryToPayOrders.do");
    //取消订单
    public static final String CANCELORDER=BASE_SERVER.concat("/order/cancelOrder.do");
    //查询订单状态详情
    public static final String QUERORDERBYID=BASE_SERVER.concat("/order/queryOrderById.do");
    //申请退款
    public static final String REFUNREQUEST=BASE_SERVER.concat("/order/refundRequest.do");
    //确认收货
    public static final String CONFIRMRECEIPT=BASE_SERVER.concat("/order/confirmReceipt.do");
    //查询退款状态
    public static final String GETREFUNDINFO=BASE_SERVER.concat("/order/getRefundInfo.do");
    //商品评论
    public static final String INSERTCOMMENT=BASE_SERVER.concat("/commodity/insertComment.do");
    //评论中心
    public static final String QUERYLOGISTIC=BASE_SERVER.concat("/logistic/queryLogistic.do");
    //版本检测更新
    public static final String CHECKNEWST=BASE_SERVER.concat("/system/checkNewestVersion.do");
    //物流查询
    public static final String LOGISTIC=BASE_SERVER.concat("/logistic/queryLogistic.do");
    //启动壁纸
    public static final String ADVERTPIC=BASE_SERVER.concat("/system/getAdvertPic.do");
    //热门搜索记录
    public static final String HOTSEARCH=BASE_SERVER.concat("/search/hotSearch.do");
    //修改用户昵称
    public static final String UPDATENICKNAME=BASE_SERVER.concat("/user/updateNickname.do");
    //获得微信支付公钥
    public static final String WEIXINGETPRIVATEKEY=BASE_SERVER.concat("/weixinpay/getPrivateKey.do");






}
