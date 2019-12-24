 //控制层 
app.controller('sellerController' ,function($scope,$controller   ,sellerService){	
	
	$controller('baseController',{$scope:$scope});//继承

	//查询实体
	$scope.findOne=function(id){
		sellerService.findOne(id).success(
			function(response){
				$scope.entity= response;
			}
		);
	}
	
	//保存 --- 注册/修改
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=sellerService.update( $scope.entity ); //修改  
		}else{
			serviceObject=sellerService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	// $scope.reloadList();//重新加载
					location.href="../../shopLogin.html"
				}else{
					alert(response.message);
				}
			}		
		);				
	}


});	
