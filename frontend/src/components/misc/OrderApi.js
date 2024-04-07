import axios from 'axios'
import {config} from '../../Constants'
import {parseJwt} from './Helpers'

export const orderApi = {
    authenticate,
    signup,
    numberOfUsers,
    numberOfOrders,
    getUsers,
    deleteUser,
    getOrders,
    deleteOrder,
    createOrder,
    getUserMe,
    postApiCall,
    getApiCall,
    deleteApiCall
}

async function postApiCall(userJson,path, body) {
    try{
    const response = await instance.post(path, body, {
        headers: {'Content-type': 'application/json', 'Authorization': bearerAuth(userJson)}
    })
    if (response.status == 201||response.status == 200) {
        return {
            statusCode: response.status,
            data: response.data
        };
    } else {
        return{
            statusCode:response.status,
            message:response.message
        }
    }}
    catch (e) {
        return{
            statusCode:409,
            message:e.message
        }
    }
}
async function getApiCall(userJson,path) {
    const response = await instance.get(path, {
        headers: {'Authorization': bearerAuth(userJson)}
    })

    if (response.status == 200) {
        return {
            statusCode: response.status,
            data: response.data
        };
    } else {
        return{
            statusCode:response.status,
            message:response.message
        }
    }
}

async function deleteApiCall(userJson,path) {
    const response = await instance.delete(path, {
        headers: {'Authorization': bearerAuth(userJson)}
    })

    if (response.status == 200||response.status == 204) {
        return {
            statusCode: response.status,
            data: response.data
        };
    } else {
        return{
            statusCode:response.status,
            message:response.message
        }
    }
}

function authenticate(username, password) {
    return instance.post('/auth/authenticate', {username, password}, {
        headers: {'Content-type': 'application/json'}
    })
}

function signup(user) {
    return instance.post('/auth/signup', user, {
        headers: {'Content-type': 'application/json'}
    })
}

function numberOfUsers() {
    return instance.get('/public/numberOfUsers')
}

function numberOfOrders() {
    return instance.get('/public/numberOfOrders')
}

function getUsers(user, username) {
    const url = username ? `/api/users/${username}` : '/api/users'
    return instance.get(url, {
        headers: {'Authorization': bearerAuth(user)}
    })
}

function deleteUser(user, username) {
    return instance.delete(`/api/users/${username}`, {
        headers: {'Authorization': bearerAuth(user)}
    })
}

function getOrders(user, text) {
    const url = text ? `/api/orders?text=${text}` : '/api/orders'
    return instance.get(url, {
        headers: {'Authorization': bearerAuth(user)}
    })
}

function deleteOrder(user, orderId) {
    return instance.delete(`/api/orders/${orderId}`, {
        headers: {'Authorization': bearerAuth(user)}
    })
}

function createOrder(user, order) {
    return instance.post('/api/orders', order, {
        headers: {
            'Content-type': 'application/json',
            'Authorization': bearerAuth(user)
        }
    })
}

function getUserMe(user) {
    return instance.get('/api/users/me', {
        headers: {'Authorization': bearerAuth(user)}
    })
}

// -- Axios

const instance = axios.create({
    baseURL: config.url.API_BASE_URL
})

instance.interceptors.request.use(function (config) {
    // If token is expired, redirect user to login
    if (config.headers.Authorization) {
        const token = config.headers.Authorization.split(' ')[1]
        const data = parseJwt(token)
        if (Date.now() > data.exp * 1000) {
            window.location.href = "/login"
        }
    }
    return config
}, function (error) {
    return Promise.reject(error)
})

// -- Helper functions

function bearerAuth(user) {
    return `Bearer ${user.accessToken}`
}