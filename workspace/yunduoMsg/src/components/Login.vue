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
              <a href="" @click="" class="a_biaoqian">没有账号？ 立即注册</a>
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
              <el-button type="primary" class="login_menu"> 登录</el-button>
            </el-form-item>

          </el-form>
        </el-card>
      </div>
    </el-col>
  </el-row>

  <div class="footer">
    <span>© 2020 欣酋科技 · </span>
    <a href="https://tsm.miit.gov.cn/dxxzsp/">京 ICP 证 394309 号 · </a>
    <a
      href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=11010802020088"
    >
      <img
        alt=""
        src="https://pic3.zhimg.com/80/v2-d0289dc0a46fc5b15b3363ffa78cf6c7.png"
      />
      京公网安备 11010802010012 号 ·
    </a>
    <a href="https://zhstatic.zhihu.com/assets/zhihu/publish-license.jpg">
      出版物经营许可证
    </a>
  </div>
  <div class="footer2">
      <span
      >侵权举报 · 网上有害信息举报专区 · 儿童色情信息举报专区 ·
        违法和不良信息举报：010-82716601</span
      >
  </div>
</div>

</template>
<script>
// 引入api.js  好调用里面的接口
import { requestLogin, setVfCode, addUser } from '../api/api'
    export default {
    name: 'login',
    data() {
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
            password: ''
        },
        LoginLoading: false,
        table1show: true,
        table2show: false,
        isDisabled: false, // 控制按钮是否可以点击（false:可以点击，true:不可点击）
        content: '获取验证码', // 发送验证码按钮的初始显示文字
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
                } else {
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
