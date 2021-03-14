<template>
<div id="logo">
  <el-row>
    <el-col :span="8" :offset="8">
      <div class="login-box">
        <el-card class="box-card">
          <div align="center">
            <h2>云朵信息</h2>
          </div>
          <el-form
            ref="loginFormRef"
            :model="loginInfo"
            :rules="loginRules"
            v-loading="LoginLoading"
          >
            <el-form-item class="el_form_item_select">
              <el-button type="primary" size="small" round @click="changeVFLogin"
              >短信登录</el-button
              >
              <el-button type="success" size="small" round @click="changPwdLogin"
              >密码登录</el-button
              >
            </el-form-item>


            <!--验证码登录-->
            <!--手机号-->
            <el-form-item class="form_item_vf" prop="mobile" v-if="table1show">
              <el-input
                v-model="loginInfo.mobile"
                maxlength="11"
                class="vf_mobile"
                prefix-icon="el-icon-mobile-phone"
              >
              </el-input>
            </el-form-item>
            <!--验证码输入框-->
            <el-form-item class="form_item_vf" prop="vfCode" v-if="table1show">
              <el-input
                type="password"
                class="vf_input"
                maxlength="6"
                v-model="loginInfo.vfCode"
                prefix-icon="el-icon-lock"
              ></el-input>
              <!--验证码倒计时-->
              <el-button
                class="send_vfCode"
                type="success"
                plain
                @click="sendVfCode"
                v-if="!isDisabled"
              >{{ content }}</el-button
              >
              <el-button
                class="send_vfCode"
                type="success"
                plain
                v-if="isDisabled"
                disabled
              >{{ content }}</el-button
              >
            </el-form-item>

            <!--账号密码登录-->
            <!--手机号-->
            <el-form-item prop="mobile" v-if="table2show">
              <el-input
                v-model="loginInfo.mobile"
                maxlength="11"
                prefix-icon="el-icon-mobile-phone"
              >
              </el-input>
            </el-form-item>
            <!--密码-->
            <el-form-item prop="password" v-if="table2show">
              <el-input
                type="password"
                v-model="loginInfo.password"
                maxlength="21"
                @keyup.enter.native="userLogin('loginInfo')"
                prefix-icon="el-icon-lock"
                show-password
              >
              </el-input>
            </el-form-item>



            <el-form-item class="el_form_item_rem">
              <el-checkbox v-model="checked" size="mini" class="rem_username">记住账号</el-checkbox>
              <a @click="openAddUser" class="a_biaoqian">没有账号？ 立即注册</a>
            </el-form-item>

            <el-form-item class="other_login_box">
              社交账号登录
              <el-button
                class="other_login"
                type="primary"
                icon="iconfont icon-qq"
                circle
              ></el-button>
              <el-button
                class="other_login"
                type="success"
                icon="iconfont icon-weixin"
                circle
              ></el-button>
              <el-button
                class="other_login"
                type="warning"
                icon="iconfont icon-weibo"
                circle
              ></el-button>
            </el-form-item>

            <el-form-item class="login_button">
              <el-button type="primary" @click="userLogin" class="login_menu"> 登录</el-button>
            </el-form-item>

          </el-form>
        </el-card>
      </div>
    </el-col>
  </el-row>

  <div class="footer">
    <span>© 2020 欣酋科技 · </span>
    <a href="https://tsm.miit.gov.cn/dxxzsp/">汝 ICP 证 394309 号 · </a>
    <a
      href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=11010802020088"
    >
      <img
        alt=""
        src="https://pic3.zhimg.com/80/v2-d0289dc0a46fc5b15b3363ffa78cf6c7.png"
      />
      汝公网安备 11010802020012 号 ·
    </a>
    <a href="https://zhstatic.zhihu.com/assets/zhihu/publish-license.jpg">
      干饭王经营许可证
    </a>
  </div>
  <div class="footer2">
      <span
      >侵权举报 · 网上吃饭点餐举报专区 · 落叶的位置 ·
        违法和不良饭店举报：010-82716601</span
      >
  </div>


  <!--新增界面-->
  <el-dialog
    title="用户注册"
    :visible.sync="dialogVisible"
    :modal-append-to-body='false'
    width="30%"
    :close-on-click-modal="false"
  >
    <el-form
      :model="addLoginInfo"
      :rules="addRules"
      ref="AddFormRef"
      label-width="0px"
      v-loading="AddLoading"
    >
      <el-form-item prop="nickName">
        <el-input
          type="text"
          v-model="addLoginInfo.nickName"
          auto-complete="off"
          placeholder="用户名"
          maxlength="20"
        ></el-input>
      </el-form-item>
      <el-form-item prop="mobile">
        <el-input
          type="text"
          v-model="addLoginInfo.mobile"
          auto-complete="off"
          placeholder="手机号"
          maxlength="11"
        ></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          type="password"
          v-model="addLoginInfo.password"
          auto-complete="off"
          placeholder="密码"
          maxlength="21"
          show-password
        ></el-input>
      </el-form-item>
      <el-form-item prop="checkPassword">
        <el-input
          type="password"
          v-model="addLoginInfo.checkPassword"
          auto-complete="off"
          placeholder="重复密码"
          maxlength="21"
          show-password
        ></el-input>
      </el-form-item>
      <el-form-item class="add_button">
        <el-button size="medium" type="info" @click="closedialogVisible" round>取消</el-button>
        <el-button size="medium" type="success" round @click="addUser">确定</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</div>

</template>
<script>
// 引入api.js  好调用里面的接口
import {requestLogin, setVfCode} from '../api/api'
    export default {
    name: 'login',
    data() {
        // 密码验证
        const pwdCheck = async(rule, value, callback) => {
            // let reg = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[~@#$%\*-\+=:,\\?\[\]\{}]).{6,16}$/
            if (value.length < 6) {
                this.changeFlag = 2;
                return callback(new Error('密码不能少于6位！'));
            } else if (value.length > 16) {
                this.changeFlag = 2;
                return callback(new Error('密码最长不能超过16位！'));
            } else {
                this.changeFlag = 1;
                callback()
            }
        }
        // 重复密码验证
        const pwdAgainCheck = async(rule, value, callback) => {
            if (value.length < 1) {
                this.changeAgainFlag = 2;
                return callback(new Error('重复密码不能为空！'));
            } else if(this.addLoginInfo.password != this.addLoginInfo.checkPassword){
                this.changeAgainFlag = 2;
                return callback(new Error('两次输入密码不一致！'));
            }else{
                this.changeAgainFlag = 1;
                callback()
            }
        };
    return {
        loginInfo: {
            mobile: '',
            vfCode: '',
            password: ''
        },
        addLoginInfo: {
            mobile: '',
            nickName: '',
            vfCode: '',
            password: '',
            checkPassword: ''
        },
        changeFlag: 0,
        changeAgainFlag: 0,
        checked: false,
        dialogVisible: false, //注册页面 默认关闭
        AddLoading: false,
        LoginLoading: false,
        table1show: true,
        table2show: false,
        isDisabled: false, // 控制按钮是否可以点击（false:可以点击，true:不可点击）
        content: '获取验证码', // 发送验证码按钮的初始显示文字
        addRules: {
            nickName: [{
                required: true,
                message: '请输入用户名',
                trigger: 'blur'
            }
            ],
            mobile: [
                {
                    required: true,
                    message: '请输入手机号',
                    trigger: 'blur'
                },
                {
                    pattern: /^1[34578]\d{9}$/,
                    message: '请输入正确的手机号',
                    trigger: 'blur'
                }
            ],
            password: [
                { required: true, validator: pwdCheck, trigger: 'blur' }
            ],
            checkPassword: [
                { required: true, validator: pwdAgainCheck, trigger: 'blur' }
            ]
        },
        loginRules: {
            nickName: [{
                required: true,
                message: '请输入用户名',
                trigger: 'blur'
            }
            ],
            mobile: [
                {
                    required: true,
                    message: '请输入手机号',
                    trigger: 'blur'
                },
                {
                    pattern: /^1[34578]\d{9}$/,
                    message: '请输入正确的手机号',
                    trigger: 'blur'
                }
            ],
            vfCode: [
                {
                    required: true,
                    message: '请输入短信验证码',
                    trigger: 'blur'
                },
                { min: 6, max: 6, message: '请输入6位短信验证码', trigger: 'blur' }
            ],
            password: [
                {
                    required: true,
                    message: '请输入密码',
                    trigger: 'blur'
                },
                { min: 8, max: 21, message: '请输入正确的密码', trigger: 'blur' }
            ],
            checkPassword: [
                {
                    required: true,
                    message: '请输入密码',
                    trigger: 'blur'
                },
                { min: 8, max: 21, message: '请输入正确的密码', trigger: 'blur' }
            ]
        }
    };
    },
    methods: {
        // 记住用户
        rememberUser () {
            // 判断复选框是否被勾选 勾选则调用配置cookie方法
            if (this.checked === true) {
                // 传入账号名，密码，和保存天数三个参数
                this.setCookie(this.loginInfo.mobile, this.loginInfo.password, 7)
            } else {
                // 清空Cookie
                this.clearCookie()
            }
        },
        // 设置cookie
        setCookie (mobile, password, remeberTime) {
            const exdate = new Date() // 获取时间
            exdate.setTime(exdate.getTime() + 24 * 60 * 60 * 1000 * remeberTime) // 保存的天数
            // 字符串拼接cookie
            window.document.cookie =
                'mobile' + '=' + mobile + ';path=/;expires=' + exdate.toGMTString()
            window.document.cookie =
                'password' + '=' + password + ';path=/;expires=' + exdate.toGMTString()
        },
        // 读取cookie
        getCookie: function () {
            if (document.cookie.length > 0) {
                const arr = document.cookie.split('; ') // 这里显示的格式需要切割一下自己可输出看下
                for (let i = 0; i < arr.length; i++) {
                    const arr2 = arr[i].split('=') // 再次切割
                    // 判断查找相对应的值
                    if (arr2[0] === 'mobile') {
                        this.loginInfo.mobile = arr2[1] // 保存到保存数据的地方
                    } else if (arr2[0] === 'password') {
                        this.loginInfo.password = arr2[1]
                    }
                }
            }
        },
        // 清除cookie
        clearCookie: function () {
            this.setCookie('', '', -1) // 修改两个值都为空，天数为-1天就好了
        },
        // 关闭用户注册框
        closedialogVisible () {
            this.resetAddFrom()
            this.dialogVisible = false
        },
        // 打开用户注册弹窗
        openAddUser(){
            this.dialogVisible = true
        },
        // 重置用户注册表单
        resetAddFrom () {
            this.$refs.AddFormRef.resetFields()
        },
        // 登录方式切换
        changeVFLogin () {
            // this.resetLoginFrom()
            this.table1show = true
            this.table2show = false
        },
        // 登录方式切换
        changPwdLogin () {
            // this.resetLoginFrom()
            this.table1show = false
            this.table2show = true
        },
        // 登录发送验证码
        sendVfCode () {
            // 校验手机号
            if (!/^1[34578]\d{9}$/.test(this.loginInfo.mobile)) {
                this.$message({
                    message: '请输入正确的手机号',
                    type: 'warning'
                })
                return
            }
            // 发送验证码
            setVfCode(this.loginInfo).then(data => {
                if (data.code === 200) {
                    this.$message({
                        message: data.msg,
                        type: 'success'
                    })
                    // 控制倒计时及按钮是否可以点击
                    const TIME_COUNT = 60
                    this.count = TIME_COUNT
                    this.timer = window.setInterval(() => {
                        if (this.count > 0 && this.count <= TIME_COUNT) {
                            // 倒计时时不可点击
                            this.isDisabled = true
                            // 计时秒数
                            this.count--
                            // 更新按钮的文字内容
                            this.content = this.count + 's后重新获取'
                        } else {
                            // 倒计时完，可点击
                            this.isDisabled = false
                            // 更新按钮文字内容
                            this.content = '获取验证码'
                            // 清空定时器!!!
                            clearInterval(this.timer)
                            this.timer = null
                        }
                    }, 1000)
                } else if (data.code === 410){
                    this.$confirm('当前手机号未注册 是否注册?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        // 打开注册页面
                        this.dialogVisible = true
                    }).catch(() => {
                        // 不作任何操作
                    })
                } else {
                    this.$message({
                        message: data.msg,
                        type: 'error'
                    })
                }
            })
        },
        // 用户登录
        userLogin () {
            this.$refs.loginFormRef.validate(valid => {
                if (valid) {
                    // 验证通过 可以提交
                    this.LoginLoading = true
                    // 调用函数  传递参数 获取结果
                    requestLogin(this.loginInfo).then(data => {
                        if (data.code === 200) {
                            this.avatar = data.data.avatar
                            // 记住用户
                            this.rememberUser()
                            this.LoginLoading = false
                            this.$message({
                                message: data.msg,
                                type: 'success'
                            })
                            // 登录成功 将token保存
                            localStorage.setItem('token', data.data.token)
                            // 保存用户信息
                            localStorage.setItem('userId', data.data.id)
                            // 保存权限信息
                            localStorage.setItem('permission', data.data.permission)
                            // 用vue路由跳转到后台主界面
                            this.$router.push(
                                {
                                    path: '/home'
                                }
                            )
                        } else {
                            this.LoginLoading = false
                            this.$message({
                                message: data.msg,
                                type: 'error'
                            })
                        }
                    })
                } else {
                    return false
                }
            })
        },
        // 用户注册
        addUser () {
            this.$refs.AddFormRef.validate(valid => {
                if (valid) {
                    // 如果验证通过 提交
                    this.AddLoading = true
                    // 调用方法
                    addUser(this.addLoginInfo).then(data => {
                        if (data.code === 200) {
                            this.AddLoading = false
                            this.dialogVisible = false
                            this.$message({
                                message: data.msg,
                                type: 'success'
                            })
                        } else {
                            this.AddLoading = false
                            this.$message({
                                message: data.msg,
                                type: 'error'
                            })
                        }
                    })
                }
            })
        }
    }
    };
</script>

<style scoped>
  @import url('//at.alicdn.com/t/font_2046038_zzqlop0onuf.css');
  .box-card{
    width: 460px;
    height: 500px;
  }
  .login-box{
    margin-top: 150px;
  }
  .send_vfCode {
    margin-left: 35px;
    width: 30%;
  }
  .other_login {
    margin-left: 10px;
  }
  #logo{
    background: url("../assets/image/bg_login.jpg");
    background-size: 100% 100%;
    height: 100%;
    position: fixed;
    width: 100%
  }
  .el_form_item_select {
    text-align: center;
  }


  .footer {
    font-size: 12px;
    color: white;
    position: absolute;
    bottom: 0;
    left: 50%;
    transform: translate(-50%, -100%);
  }
  .footer2 {
    font-size: 12px;
    color: white;
    position: absolute;
    bottom: 0;
    left: 50%;
    transform: translate(-50%, -20%);
  }
  .vf_mobile{
    width: 100%;
  }
  .vf_input {
    width: 60%;
  }
  .form_item_vf{
    text-align: center;
  }
  .a_biaoqian{
    text-decoration:none;
    padding-left: 150px;
    cursor: pointer;
  }
  .rem_username{
    padding-left: 3px;
  }
  .login_button{
    text-align: center;
  }
  .login_menu {
    width: 350px;
  }
  .other_login_box{
    padding-left: 5px;
  }
</style>
