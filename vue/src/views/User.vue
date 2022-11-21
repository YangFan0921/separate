<template>
    <div>

        <div>
            <div style="padding: 10px 0">
                <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="username"></el-input>
                <el-input style="width: 200px" placeholder="请输入邮箱" suffix-icon="el-icon-message" v-model="email"></el-input>
                <el-input style="width: 200px" placeholder="请输入地址" suffix-icon="el-icon-position" v-model="address"></el-input>
                <el-button type="primary" class="ml-5" @click="load">搜索</el-button>
                <el-button type="warning"  @click="reset">重置</el-button>
            </div>

            <div style="padding: 10px 0">
                <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
                <el-popconfirm
                        class="ml-5"
                        confirm-button-text='确定'
                        cancel-button-text='取消'
                        icon="el-icon-info"
                        icon-color="red"
                        title="您确定批量删除吗？"
                        @confirm="delBatch"
                >
                    <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
                </el-popconfirm>
                <el-upload action="http://localhost:9090/user/import" style="display: inline-block" :show-file-list="false" accept="xlsx" :on-success="handleImportSuccess">
                    <el-button type="primary" class="ml-5" @click="">导入 <i class="el-icon-bottom"></i></el-button>
                </el-upload>
                <el-button type="primary" @click="exportUser" class="ml-5">导出 <i class="el-icon-top"></i></el-button>
            </div>
        </div>

        <el-table :data="tableData" border stripe header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="id" label="id" width="80"></el-table-column>
            <el-table-column prop="username" label="用户名" width="140"></el-table-column>
            <el-table-column prop="role" label="角色" width="120">
                <template slot-scope="scope">
                    <el-tag type="primary" v-if="scope.row.role === 'ROLE_ADMIN'">管理员</el-tag>
                    <el-tag type="warning" v-if="scope.row.role === 'ROLE_TEACHER'">老师</el-tag>
                    <el-tag type="success" v-if="scope.row.role === 'ROLE_STUDENT'">学生</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="nickname" label="昵称" width="120"></el-table-column>
            <el-table-column prop="email" label="邮箱" width="140"></el-table-column>
            <el-table-column prop="phone" label="手机号" width="120"></el-table-column>
            <el-table-column prop="address" label="地址"></el-table-column>
            <el-table-column label="操作" width="500px" align="right">
                <template slot-scope="scope" >
                    <el-button type="primary" @click="lookCourse(scope.row.courses)" v-if="scope.row.role === 'ROLE_TEACHER'">查看教授课程 <i class="el-icon-notebook-2"></i></el-button>
                    <el-button type="warning" @click="lookStuCourse(scope.row.stuCourses)" v-if="scope.row.role === 'ROLE_STUDENT'">查看已选课程 <i class="el-icon-notebook-2"></i></el-button>
                    <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
                    <el-popconfirm
                            class="ml-5"
                            confirm-button-text='确定'
                            cancel-button-text='取消'
                            icon="el-icon-info"
                            icon-color="red"
                            title="您确定删除吗？"
                            @confirm="del(scope.row.id)"
                    >
                        <el-button type="danger" slot="reference" >删除 <i class="el-icon-remove-outline"></i></el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <div style="padding: 10px 0">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="pageNum"
                    :page-sizes="[5, 10, 15, 20]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>

        <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="60%">
            <el-form label-width="120px" size="small">
                <el-form-item label="用户名" >
                    <el-input v-model="form.username" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="角色" >
                    <el-select clearable v-model="form.role" placeholder="请选择角色" style="width: 100%">
                        <el-option v-for="item in roles" :key="item.name" :label="item.name" :value="item.flag" >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="昵称" >
                    <el-input v-model="form.nickname" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" >
                    <el-input v-model="form.email" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="手机号" >
                    <el-input v-model="form.phone" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="地址" >
                    <el-input v-model="form.address" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer" >
                <el-button @click="dialogFormVisible=false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="课程信息" :visible.sync="dialogCourseVisible" width="50%">
            <el-table :data="courses" border stripe>
                <el-table-column prop="name" label="课程名称" ></el-table-column>
                <el-table-column prop="score" label="学分" width="70px"></el-table-column>
                <el-table-column prop="times" label="课时" width="70px"></el-table-column>
                <el-table-column prop="state" label="开课" width="70px">
                    <template slot-scope="scope">
                        <el-tag type="success" v-if="scope.row.state == true">是</el-tag>
                        <el-tag type="warning" v-if="scope.row.state == false">否</el-tag>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>
        <el-dialog title="课程信息" :visible.sync="dialogStudentCourseVisible" width="50%">
            <el-table :data="stuCourses" border stripe>
                <el-table-column prop="name" label="课程名称" ></el-table-column>
                <el-table-column prop="score" label="学分" width="70px"></el-table-column>
                <el-table-column prop="times" label="课时" width="70px"></el-table-column>
            </el-table>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "User",
        data(){
            return{
                tableData: [],
                pageNum:1,
                pageSize:20,
                total:0,
                username:"",
                email:"",
                address:"",
                dialogFormVisible:false,
                form:{},
                multipleSelection:[],
                headerBg:'headerBg',
                roles:[],
                dialogCourseVisible:false,
                courses:[],
                stuCourses:[],
                dialogStudentCourseVisible:false,
            }
        },
        created() {
            this.load()
        },
        methods:{
            load(){
                this.request.get("/user/page",{
                    params:{
                        pageNum:this.pageNum,
                        pageSize:this.pageSize,
                        username:this.username,
                        email:this.email,
                        address:this.address
                    }}).then(res => {
                    // console.log(res)
                    this.tableData = res.records
                    this.total = res.total
                })
                this.request.get("/role").then(res => {
                    // console.log("/role---->",res)
                    this.roles = res
                })
            },
            reset(){
                this.username = ""
                this.email = ""
                this.address = ""
                this.load()
            },
            lookCourse(courses){
                this.dialogCourseVisible = true
                this.courses = courses
                // console.log(courses)
            },
            lookStuCourse(stuCourses){
                this.dialogStudentCourseVisible = true
                this.stuCourses = stuCourses
                // console.log(stuCourses)
            },
            handleAdd(){
                this.dialogFormVisible = true
                this.form={}

            },
            handleEdit(row){
                this.form = row
                this.dialogFormVisible = true
            },

            save(){
                this.request.post("/user/save",this.form).then(res => {
                    if (res == 1){
                        this.$message.success("保存成功")
                        this.dialogFormVisible = false
                        this.load()
                    }else {
                        this.$message.error("保存失败")
                    }
                })
            },
            del(id){
                this.request.get("/user/delete/"+id).then(res => {
                    if (res == 1){
                        this.$message.success("删除成功")
                        this.load()
                    }else {
                        this.$message.error("删除失败")
                    }
                })
            },
            handleSelectionChange(val){
                // console.log(val)
                this.multipleSelection = val
            },
            delBatch(){
                let ids = this.multipleSelection.map(v => v.id)
                // console.log(ids)
                this.request.post("/user/delete/batch",ids).then(res => {
                    if (res >= 1){
                        this.$message.success("批量删除成功")
                        this.load()
                    }else {
                        this.$message.error("批量删除失败")
                    }
                })
            },
            exportUser(){
                window.open("http://localhost:9090/user/export")
            },
            handleImportSuccess(){
              this.$message.success("导入成功")
                this.load()
            },
            handleSizeChange(pageSize) {
                // console.log(`每页 ${pageSize} 条`);
                this.pageSize = pageSize;
                this.load()
            },
            handleCurrentChange(pageNum) {
                // console.log(`当前页: ${pageNum}`);
                this.pageNum = pageNum
                this.load()
            }
        }
    }
</script>

<style>
    .headerBg{
        background-color: #eee !important;
    }
    /*.el-table th.el-table__cell>.cell {*/
    /*    text-align: center;*/
    /*}*/
</style>