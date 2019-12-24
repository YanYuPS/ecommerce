 //控制层 
app.controller('goodsController' ,function($scope,$http, $controller,userService){
	
	$controller('baseController',{$scope:$scope});//继承


	//------------------------------------------------------------------------------------------------

	//***************顶部--登录名***************

	$scope.loginFlag=false;
	$scope.username='';
	//获取用户名
	$scope.getName=function(){
		userService.getName().success(
			function(response){
				$scope.loginFlag=response.success;
				if(response.success){
					$scope.username=response.message;
				}
			}
		);
	}

	//***************侧边栏--购物车***************

	// $scope.cartList=[]
	//查询购物车列表
	$scope.findCartList=function(){
		$http.get('http://localhost:9107/ecommerce_cart_web/cart/findCartList.do',
			{'withCredentials':true}).success(
			function(response){
				$scope.cartList=response;
				$scope.totalValue= $scope.sum(response);
				console.log($scope.cartList)
				console.log($scope.totalValue)
			}
		);
	}
	//求合计数
	$scope.sum=function(cartList){
		var totalValue={totalNum:0,totalMoney:0 };

		for(var i=0;i<cartList.length ;i++){
			var cart=cartList[i];//购物车对象
			for(var j=0;j<cart.orderItemList.length;j++){
				var orderItem=  cart.orderItemList[j];//购物车明细
				totalValue.totalNum+=orderItem.num;//累加数量
				totalValue.totalMoney+=orderItem.totalFee;//累加金额
			}
		}
		return totalValue;
	}
	//商品详情地址
	$scope.getUrl=function(goodsId){
		return "http://localhost:9105/ecommerce_html_web/admin/"+goodsId+".html";
	}
	//数量加减
	$scope.addGoodsToCartList=function(itemId,num){
		$http.get('http://localhost:9107/ecommerce_cart_web/cart/addGoodsToCartList.do?itemId='+itemId+'&num='+num,
			{'withCredentials':true}).success(
			function(response){
				if(response.success){//如果成功
					$scope.findCartList();//刷新列表
				}else{
					alert(response.message);
				}
			}
		);
	}

	//***************搜索***************

	//搜索跳转
	$scope.search=function(){
		location.href="http://localhost:9104/ecommerce_search_web/#?keywords="+$scope.keywords;
	}
	//----------------------------------------------------------------------------------------------------


	//数量操作
	$scope.addNum=function(x){
		$scope.num=$scope.num+x;
		if($scope.num<1){
			$scope.num=1;
		}
	}

	$scope.specificationItems={};//记录用户选择的规格
	//用户选择规格
	$scope.selectSpecification=function(name,value){	
		$scope.specificationItems[name]=value;

		searchSku();//读取sku
	}	
	//判断某规格选项是否被用户选中
	$scope.isSelected=function(name,value){
		if($scope.specificationItems[name]==value){
			return true;
		}else{
			return false;
		}		
	}

	//加载默认SKU
	$scope.loadSku=function(){
		$scope.sku=skuList[0];		
		$scope.specificationItems= JSON.parse(JSON.stringify($scope.sku.spec)) ;
	}

	//匹配两个对象
	matchObject=function(map1,map2){		
		for(var k in map1){
			if(map1[k]!=map2[k]){
				return false;
			}			
		}
		for(var k in map2){
			if(map2[k]!=map1[k]){
				return false;
			}			
		}
		return true;		
	}

	//查询SKU
	searchSku=function(){
		for(var i=0;i<skuList.length;i++ ){
			if( matchObject(skuList[i].spec ,$scope.specificationItems ) ){
				$scope.sku=skuList[i];
				return ;
			}			
		}	
		$scope.sku={id:0,title:'--------',price:0};//如果没有匹配的		
	}

	//添加商品到购物车
	$scope.addToCart=function(){
		console.log("addToCart")
		$http.get('http://localhost:9107/ecommerce_cart_web/cart/addGoodsToCartList.do?itemId='
			+ $scope.sku.id +'&num='+$scope.num,{'withCredentials':true}).success(
			function(response){
				console.log(response)
				if(response.success){
					// location.href='http://localhost:9107/ecommerce_cart_web/admin/cart.html';//跳转到购物车页面
					$scope.findCartList();//刷新购物车
				}else{
					alert(response.message);
				}
			}
		);
	}


	
});	
