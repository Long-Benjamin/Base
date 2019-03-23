package cn.com.iyin.utils

import android.content.Context
import com.bumptech.glide.Glide
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import kotlin.concurrent.thread


/**
 * 清空缓存工具类
 */
object CacheUtil {

    /**
     * 清楚 Glide 的缓存
     */
    fun clearGlideCache(context: Context){

        //清理内存缓存 可以在UI主线程中进行
        Glide.get(context).clearMemory()

        //清理磁盘缓存 需要在子线程中执行
        thread(start = true){
            Glide.get(context).clearDiskCache()
        }
    }

    fun clearSignImageCache(): Boolean {
        return delAllFile(PathHelper.getSignImagePath(""))
    }

    //删除指定文件夹下所有文件
    //param path 文件夹完整绝对路径
    fun delAllFile(path: String): Boolean {
        var flag = false
        val file = File(path)
        if (!file.exists()) {
            return flag
        }
        if (!file.isDirectory) {
            return flag
        }
        val tempList = file.listFiles()
        var temp: File?
        for (i in tempList!!.indices) {
            if (path.endsWith(File.separator)) {
                temp = File(path + tempList[i])
            } else {
                temp = File(path + File.separator + tempList[i])
            }
            if (temp.isFile) {
                temp.delete()
            }
            if (temp.isDirectory) {
                delAllFile(path + "/" + tempList[i])//先删除文件夹里面的文件
                flag = true
            }
        }
        return flag
    }


    /**
     * 获取指定文件夹的大小
     *
     * @param f
     * @return
     * @throws Exception
     */
    fun getFileSizes(f: File): Long {
        var size: Long = 0
        val flist = f.listFiles()
                ?: //4.2的模拟器空指针。
                return 0//文件夹目录下的所有文件
        if (flist != null) {
            for (i in flist.indices) {
                size += if (flist[i].isDirectory) {//判断是否父目录下还有子目录
                    getFileSizes(flist[i])
                } else {
                    getFileSize(flist[i])
                }
            }
        }
        return size
    }


    /**
     * 获取指定文件的大小
     *
     * @return
     * @throws Exception
     */
    fun getFileSize(file: File): Long {

        var size: Long = 0
        if (file.exists()) {
            var fis: FileInputStream? = null
            try {
                fis = FileInputStream(file)//使用FileInputStream读入file的数据流
                size = fis.available().toLong()//文件的大小
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    fis!!.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

        } else {
        }
        return size
    }


}