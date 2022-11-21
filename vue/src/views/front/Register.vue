<template>
    <div class="wrapper">
        <div style="margin: 100px auto; background-color: #fff; width: 350px; height: 410px; padding: 20px; border-radius: 10px">
            <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>注 册</b></div>
            <el-form :model="user" :rules="rules" ref="userForm">
                <el-form-item label="" prop="username">
                    <el-input placeholder="请输入用户名" size="medium" style="margin: 5px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
                </el-form-item>
                <el-form-item label="" prop="nickname">
                    <el-input placeholder="请输入昵称" size="medium" style="margin: 5px 0" prefix-icon="el-icon-user" v-model="user.nickname"></el-input>
                </el-form-item>
                <el-form-item label="" prop="password">
                    <el-input placeholder="请输入密码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
                </el-form-item>
                <el-form-item label="" prop="confirmPassword">
                    <el-input placeholder="请确认密码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="user.confirmPassword"></el-input>
                </el-form-item>
                <el-form-item style="margin: 5px 0; text-align: right">
                    <el-button type="warning" size="small"  autocomplete="off" @click="$router.push('/frontLogin')">返回登录</el-button>
                    <el-button type="primary" size="small"  autocomplete="off" @click="login">注册</el-button>
                </el-form-item>
            </el-form>


        </div>
    </div>
</template>

<script>
    export default {
        name: "Login",
        data() {
            return {
                user: {},
                rules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur' },
                        { min: 3, max: 14, message: '长度在 3 到 14 个字符', trigger: 'blur' }
                    ],
                    nickname: [
                        { required: true, message: '请输入昵称', trigger: 'blur' },
                        { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请选择密码', trigger: 'blur' },
                        { min: 3, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
                    ],
                    confirmPassword: [
                        { required: true, message: '请选择密码', trigger: 'blur' },
                        { min: 3, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
                    ],
                }
            }
        },
        methods: {
            login(){
                this.$refs['userForm'].validate((valid) => {
                    if (this.user.password !== this.user.confirmPassword){
                        this.$message.error("两次输入密码不一致")
                        return false
                    }
                    if (valid) {
                        this.request.post("/user/register",this.user).then(res =>{
                            if (res.code === '200'){
                                this.$message.success("注册成功")
                                this.$router.push("/login")
                            }else {
                                this.$message.error(res.msg)
                            }
                        })
                    } else {
                        return false;
                    }
                })

            }

        }
    }
</script>

<style>
    .wrapper {
        height: 100vh;
        background-image: linear-gradient(to bottom right, #2a5caa , #00ae9d);
        overflow: hidden;
    }
</style>
