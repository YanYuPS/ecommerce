 //控制层 
app.controller('itemCatController' ,function($scope,$controller,itemCatService,typeTemplateService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
/*
	$scope.findAll=function(){
		itemCatService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
*/

	//分页
/*
	$scope.findPage=function(page,rows){
		itemCatService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
*/

	//查询实体 
	$scope.findOne=function(id){				
		itemCatService.findOne(id).success(
			function(response){
				$scope.entity= response;
			}
		);				
	}
	
	//保存 
	$scope.save=function(){
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=itemCatService.update( $scope.entity ); //修改  
		}else{
			$scope.entity.parentId=$scope.parentId;//上级id
			serviceObject=itemCatService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		itemCatService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 

	//父级Id，默认0
	$scope.parentId=0;
	$scope.setParentId=function (value) {
		$scope.parentId=value;
	}

	//重新加载列表 数据---重写
	$scope.reloadList=function(){
		//重新加载时，清空选中
		$scope.selectIds=[];
		var nameAll = document.getElementsByName("checkboxAll");
		nameAll[0].checked = false;

		$scope.search( $scope.parentId,$scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
	}
	//搜索
	$scope.search=function(parentId,page,rows){
/*
		itemCatService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}
		);
*/
		itemCatService.searchByParentId(parentId,page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}
		);
	}

	/**
	 * 查询下级list
	 * @param parentId
	 */
/*
	$scope.findByParentId=function (parentId) {
		$scope.setParentId(parentId)

		itemCatService.findByParentId(parentId).success(
			function (response) {
				$scope.list=response;
			}
		)
	}
	$scope.findByParentIdPage=function (parentId,page,rows) {
		$scope.setParentId(parentId)

		itemCatService.findByParentIdPage(parentId,page,rows).success(
			function (response) {
				$scope.list=response.rows;
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}
		)
	}
*/

	//级别：共3级，默认为1级
	$scope.grade=1;
	$scope.setGrade=function (value) {
		$scope.grade=value;
	}
	/**
	 * 改变级别时的操作（点击面包屑导航/点击查看下级）
	 * @param parentEntity 父级对象
	 */
	$scope.changeEntity=function (parentEntity) {
		//设置当前entity
		if($scope.grade==1){//如果当前是顶级
			$scope.entity_2=null;//2级为空
			$scope.entity_3=null;//3级为空
		}else if($scope.grade==2){//如果当前是2级
			$scope.entity_2=parentEntity;//2级
			$scope.entity_3=null;//3级为空
		}else if($scope.grade==3){//如果当前是3级
			// $scope.entity_2=null;//2级设置过了
			$scope.entity_3=parentEntity;//3级
		}

		//查询当前entity的下级list
		$scope.setParentId(parentEntity.id)
		$scope.reloadList();//刷新列表
	}

	//类型模版list
	$scope.typeTemplateList={data:[]}
	$scope.selectOptionList=function () {
		typeTemplateService.selectOptionList().success(
			function (response) {
				$scope.typeTemplateList={data:response};
			}
		)
	}
});	
