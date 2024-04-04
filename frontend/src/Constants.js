const prod = {
  url: {
    API_BASE_URL: 'https://myapp.herokuapp.com',
  }
}

const dev = {
  url: {
    API_BASE_URL: 'http://localhost:8080'
  }
}
export const urlPaths={
  SIGNUP:'/auth/signup',
  CHECK_USER_REGISTRATION_STATUS:'/public/checkUserRegistrationStatus/',
  SEND_VERIFICATION:'/auth/sendVerificationChallenge',
  VALIDATE_VERIFICATION:'/auth/validateChallenge'
}

export const config = process.env.NODE_ENV === 'development' ? dev : prod