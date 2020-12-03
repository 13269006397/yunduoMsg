// 进行远程调用
import axios from 'axios'
// 每次请求携带cookies信息
axios.defaults.withCredentials = true
axios.defaults.headers['Content-Type'] = 'application/json'

// 获取章节小节
export const getChapterList = params => {
  return axios
    .post('/api-edu/chapter/chapterTree', JSON.stringify(params))
    .then(res => res.data)
}
