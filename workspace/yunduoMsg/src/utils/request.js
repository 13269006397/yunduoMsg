import axios from 'axios'
import { Loading } from 'element-ui'
import store from '../store'
import { getToken } from '@/utils/auth'
import qs from 'qs'

// axios 实例构建
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // api 的 base_url 接口请求的基路径
  timeout: 30000 // 请求超时时间
})

// 设置请求头Content-type
service.defaults.headers.post['Content-Type'] = 'application/json'

// 请求拦截
service.interceptors.request.use(
  config => {
    if (store.getters.token) {
      config.headers['X-Token'] = getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
    }
    return config
  },
  error => {
    // Do something with request error
    return Promise.reject(error)
  }
)

// 响应拦截
service.interceptors.response.use(
  response => {
    return response.data
  },
  err => {
    return Promise.reject(err) // 返回错误信息
  }
)

export default function (
  option,

  // api 请求额外配置
  {
    loading = false, // 请求 loading，默认不开启
    text = 'Loading', // loading 提示文本
    dataType = 'json' // 请求的数据格式，默认 json , 可选 formData
  } = {}
) {
  if (loading) {
    var loadingInstance = Loading.service({
      lock: true,
      text,
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)'
    })
  }
  // console.log('===========',dataType);
  // 发送 formData 数据格式
  if (dataType === 'formData') {
    // console.log('===========',dataType);

    option.headers = {
      'Content-Type': 'application/x-www-form-urlencoded' // 含文件: multipart/form-data
    }

    option.data = qs.stringify(option.data)
  }

  return new Promise((resolve, reject) => {
    service(option)
      .then(resolve)
      .catch(reject)
      .finally(() => {
        if (loading) loadingInstance.close()
      })
  })
}
