//服务层
app.service('itemCatService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../itemCat/findAll.do');		
	}

	//查询实体
	this.findOne=function(id){
		return $http.get('../itemCat/findOne.do?id='+id);
	}

	//根据上级id，返回全部下级list
	this.findByParentId=function (parentId) {
		return $http.get('../itemCat/findByParentId.do?parentId='+parentId);
	}


});
