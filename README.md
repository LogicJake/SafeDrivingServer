_action=postSignup
参数：user_name,user_password
功能：利用user_name,user_password在user数据库中新建用户，在新建之前检查是否已经存在该用户，并生成token返回
返回值：
成功：{
	status：1
	token
	id：用户编号
}
用户名已存在：{
	status：2	
}


