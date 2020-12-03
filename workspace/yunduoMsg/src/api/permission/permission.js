// 进行远程调用
import axios from 'axios'
// 每次请求携带cookies信息
axios.defaults.withCredentials = true
axios.defaults.headers['Content-Type'] = 'application/json'

// 声明一个调用方法
export const requestLogin = params => {
  return axios
    .post('/api-user/user/login/userLogin', JSON.stringify(params))
    .then(res => res.data)
}
