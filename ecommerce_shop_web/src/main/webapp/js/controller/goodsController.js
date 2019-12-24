 //控制层 
app.controller('goodsController' ,function($scope,$controller, goodsService,
										   $location,uploadService,itemCatService,typeTemplateService){
	
	$controller('baseController',{$scope:$scope});//继承

	//goods_edit
	$scope.entity={goods:{price:0},goodsDesc:{itemImages:[],specificationItems:[]}};//定义页面实体结构

	//goods
	$scope.status=['未提交','待审核','通过','驳回'];//商品状态
	$scope.itemCatList=[];//商品分类列表：下标id，内容name
	$scope.isMarket=['未上架','已上架'];//商品上架

    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}
	//分页
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	//查询实体 
	$scope.findOne=function(){
		var id= $location.search()['id'];//获取参数值
		if(id==null){
			return ;
		}

		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;

				//向富文本编辑器添加商品介绍
				editor.html($scope.entity.goodsDesc.introduction);
				//显示图片列表
				$scope.entity.goodsDesc.itemImages= JSON.parse($scope.entity.goodsDesc.itemImages);
				//显示扩展属性
				$scope.entity.goodsDesc.customAttributeItems=JSON.parse($scope.entity.goodsDesc.customAttributeItems);
				//规格
				$scope.entity.goodsDesc.specificationItems=JSON.parse($scope.entity.goodsDesc.specificationItems);
				//SKU列表规格列转换
				for( var i=0;i<$scope.entity.itemList.length;i++ ){
					$scope.entity.itemList[i].spec=JSON.parse( $scope.entity.itemList[i].spec);
				}

			}
		);				
	}
	//保存 
	$scope.save=function(){
		//商品介绍=富文本编辑器内容
		$scope.entity.goodsDesc.introduction=editor.html()

		var serviceObject;//服务层对象  				
		if($scope.entity.goods.id!=null){//如果有ID
			serviceObject=goodsService.update( $scope.entity ); //修改  
		}else{
			serviceObject=goodsService.add( $scope.entity  );//增加 
		}
		console.log($scope.entity)

		serviceObject.success(
			function(response){
				if(response.success){
					location.href="goods.html";//跳转到商品列表页
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	$scope.searchEntity={};//定义搜索对象
	//搜索
	$scope.search=function(page,rows){			
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}

	//加载商品分类列表
	$scope.findItemCatList=function(){
		itemCatService.findAll().success(
			function(response){//分类名称、分类id
				for(var i=0;i<response.length;i++){
					$scope.itemCatList[response[i].id]=response[i].name;
				}
			}
		);
	}

	//提交审核
	$scope.updateStatus=function(status){
		goodsService.updateStatus($scope.selectIds,status).success(
			function(response){
				if(response.success){
					$scope.reloadList();//重新加载
					$scope.selectIds=[];
				}else{
					alert(response.message);
				}
			}
		);
	}
	//上架下架
	$scope.updateMarket=function(status){
		goodsService.updateMarket($scope.selectIds,status).success(
			function(response){
				if(response.success){
					$scope.reloadList();//重新加载
					$scope.selectIds=[];
				}else{
					alert(response.message);
				}
			}
		);
	}

	//----------------goods_edit---------------------

	//图片

	//上传图片
	$scope.uploadFile=function(){
		uploadService.uploadFile().success(function(response) {
			if(response.success){//如果上传成功，取出url
				$scope.image_entity.url=response.message;//设置文件地址
			}else{
				alert(response.message);
			}
		}).error(function() {
			alert("上传发生错误");
		});
	};
	//添加图片列表
	$scope.add_image_entity=function(){
		$scope.entity.goodsDesc.itemImages.push($scope.image_entity);
	}
	//列表中移除图片
	$scope.remove_image_entity=function(index){
		$scope.entity.goodsDesc.itemImages.splice(index,1);
	}


	//分类
	//读取一级分类---初始化读取
	$scope.selectItemCat1List=function(){
		itemCatService.findByParentId(0).success(
			function(response){
				$scope.itemCat1List=response;
			}
		);
	}
	//读取二级分类
	//$watch方法用于监控某个变量的值，当被监控的值发生变化，就自动执行相应的函数。
	$scope.$watch('entity.goods.category1Id', function(newValue, oldValue) {
		//根据选择的值，查询二级分类
		itemCatService.findByParentId(newValue).success(
			function(response){
				$scope.itemCat2List = response;
				$scope.entity.goods.category2Id=$scope.itemCat2List[0].id
			}
		);
	});
	//读取三级分类
	$scope.$watch('entity.goods.category2Id', function(newValue, oldValue) {
		//根据选择的值，查询二级分类
		itemCatService.findByParentId(newValue).success(
			function(response){
				$scope.itemCat3List=response;
				$scope.entity.goods.category3Id=$scope.itemCat3List[0].id
			}
		);
	});
	//三级分类选择后  读取模板ID
	$scope.$watch('entity.goods.category3Id', function(newValue, oldValue) {
		itemCatService.findOne(newValue).success(
			function(response){
				$scope.entity.goods.typeTemplateId=response.typeId; //更新模板ID
			}
		);
	});


	//模版+品牌、扩展属性、规格

	//模板ID读取后，更新品牌列表
	$scope.$watch('entity.goods.typeTemplateId', function(newValue, oldValue) {

		typeTemplateService.findOne(newValue).success(
			function(response){
				$scope.typeTemplate = response;//获取类型模板
				$scope.typeTemplate.brandIds = JSON.parse($scope.typeTemplate.brandIds);//品牌列表
				$scope.entity.goods.brandId=$scope.typeTemplate.brandIds[0].id

				//如果没有ID--add
				if($location.search()['id']==null) {
					//扩展属性
					$scope.entity.goodsDesc.customAttributeItems = JSON.parse($scope.typeTemplate.customAttributeItems);
				}
			}
		);

		//查询规格列表
		typeTemplateService.findSpecList(newValue).success(
			function(response){
				$scope.specList=response;
			}
		)

	});


	//保存规格

	//从集合中按照key查询对象----list<Map>
	$scope.searchObjectByKey=function(list,key,keyValue){
		// console.log($scope.entity.goodsDesc.specificationItems)
		//有，返回list
		for(var i=0;i<list.length;i++){
			if(list[i][key]==keyValue){
				return list[i];
			}
		}
		//没有，返回空
		return null;
	}
	//ng-click
	$scope.updateSpecAttribute=function($event,specName,optionName){
		// console.log("click")
		var object= $scope.searchObjectByKey($scope.entity.goodsDesc.specificationItems,'attributeName', specName);

		if(object==null){//还没有这个规格名
			$scope.entity.goodsDesc.specificationItems.push({"attributeName":specName,"attributeValue":[optionName]});
		}else{//有了这个规格
			if($event.target.checked){//选中
				object.attributeValue.push(optionName);
			}else{//取消勾选
				object.attributeValue.splice( object.attributeValue.indexOf(optionName) ,1);//移除选项
				//如果选项都取消了，将此条记录移除
				if(object.attributeValue.length==0){
					$scope.entity.goodsDesc.specificationItems.splice($scope.entity.goodsDesc.specificationItems.indexOf(object),1);
				}
			}
		}
		// console.log($scope.entity.goodsDesc.specificationItems)
	}
	//ng-checked 设置checked属性
	//有这个（规格名，规格项）返回true， 没有返回false
	$scope.checkAttributeValue=function(specName,optionName){
		// console.log("checked")
		var object= $scope.searchObjectByKey($scope.entity.goodsDesc.specificationItems,'attributeName',specName);
		if(object==null){//还没有这个规格名
			return false;
		}else{
			if(object.attributeValue.indexOf(optionName)>=0){
				return true;
			}else{
				return false;
			}
		}
	}


	//SKU

	//创建SKU列表
	$scope.createItemList=function(){
		$scope.entity.itemList=[{spec:{},price:$scope.entity.goods.price,num:999,status:'0',isDefault:'0' } ];//初始
		var items=  $scope.entity.goodsDesc.specificationItems;
		for(var i=0;i< items.length;i++){
			$scope.entity.itemList = addColumn( $scope.entity.itemList,items[i].attributeName,items[i].attributeValue );
		}
	}
	//添加列值
	addColumn=function(list,columnName,conlumnValues){
		var newList=[];//新的集合
		for(var i=0;i<list.length;i++){
			var oldRow= list[i];
			for(var j=0;j<conlumnValues.length;j++){
				var newRow= JSON.parse( JSON.stringify( oldRow )  );//深克隆
				newRow.spec[columnName]=conlumnValues[j];
				newList.push(newRow);
			}
		}
		return newList;
	}





});	
