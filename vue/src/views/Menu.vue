<template>
    <div>

        <div>
            <div style="padding: 10px 0">
                <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
                <el-button type="primary" class="ml-5" @click="load">搜索</el-button>
                <el-button type="warning"  @click="reset">重置</el-button>
            </div>

            <div style="padding: 10px 0">
                <el-button type="primary" @click="handleAdd(null)">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
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
<!--                <el-upload action="http://localhost:9090/menu/import" style="display: inline-block" :show-file-list="false" accept="xlsx" :on-success="handleImportSuccess">-->
<!--                    <el-button type="primary" class="ml-5" @click="">导入 <i class="el-icon-bottom"></i></el-button>-->
<!--                </el-upload>-->
<!--                <el-button type="primary" @click="exportUser" class="ml-5">导出 <i class="el-icon-top"></i></el-button>-->
            </div>
        </div>

        <el-table :data="tableData" border stripe header-cell-class-name="headerBg"
                  row-key="id" default-expand-all @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="id" label="id" width="80"></el-table-column>
            <el-table-column prop="name" label="名称" width="140"></el-table-column>
            <el-table-column prop="path" label="路径"></el-table-column>
            <el-table-column prop="pagePath" label="页面路径"></el-table-column>
            <el-table-column label="图标">
                <template slot-scope="scope">
                    <i :class="scope.row.icon" style="font-size: 16px"/>
                </template>
            </el-table-column>
            <el-table-column prop="description" label="描述"></el-table-column>
            <el-table-column label="操作" width="300" align="right">
                <template slot-scope="scope">
                    <el-button type="primary" @click="handleAdd(scope.row.id)" v-if="!scope.row.pid && !scope.row.path">新增子菜单 <i class="el-icon-plus"></i></el-button>
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
<!--        <div style="padding: 10px 0">-->
<!--            <el-pagination-->
<!--                    @size-change="handleSizeChange"-->
<!--                    @current-change="handleCurrentChange"-->
<!--                    :current-page="pageNum"-->
<!--                    :page-sizes="[5, 10, 15, 20]"-->
<!--                    :page-size="pageSize"-->
<!--                    layout="total, sizes, prev, pager, next, jumper"-->
<!--                    :total="total">-->
<!--            </el-pagination>-->
<!--        </div>-->

        <el-dialog title="菜单信息" :visible.sync="dialogFormVisible" width="30%">
            <el-form label-width="120px" size="small">
                <el-form-item label="名称" >
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="路径" >
                    <el-input v-model="form.path" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="页面路径" >
                    <el-input v-model="form.pagePath" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="图标" >
                    <el-select clearable v-model="form.icon" placeholder="请选择" style="width: 100%">
                        <el-option v-for="item in options" :key="item.name" :label="item.name" :value="item.value" >
                            <i :class="item.value"> {{item.name}}</i>
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="描述" >
                    <el-input type="textarea" v-model="form.description" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer" >
                <el-button @click="dialogFormVisible=false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: "Menu",
        data(){
            return{
                tableData: [],
                // pageNum:1,
                // pageSize:5,
                // total:0,
                name:"",
                dialogFormVisible:false,
                form:{},
                multipleSelection:[],
                headerBg:'headerBg',
                options:[]
            }
        },
        created() {
            this.load()
        },
        methods:{
            reset(){
                this.name = ""
                this.description = ""
                this.load()
            },
            handleAdd(pid){
                this.dialogFormVisible = true
                this.form={}
                if (pid){
                    this.form.pid = pid
                }

            },
            handleEdit(row){
                this.form = JSON.parse(JSON.stringify(row))
                this.dialogFormVisible = true

            },
            load(){
                this.request.get("/menu",{
                    params:{
                        name:this.name,
                    }}).then(res => {
                    // console.log(res)
                    this.tableData = res
                    // this.total = res.total
                })
                // 请求图标数据
                this.request.get("/dict/icons").then(res =>{
                    // console.log("/dict/icons---->",res)
                    this.options = res
                })
            },
            save(){
                this.request.post("/menu/save",this.form).then(res => {
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
                this.request.get("/menu/delete/"+id).then(res => {
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
                this.request.post("/menu/delete/batch",ids).then(res => {
                    if (res >= 1){
                        this.$message.success("批量删除成功")
                        this.load()
                    }else {
                        this.$message.error("批量删除失败")
                    }
                })
            },
            exportUser(){
                window.open("http://localhost:9090/menu/export")
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