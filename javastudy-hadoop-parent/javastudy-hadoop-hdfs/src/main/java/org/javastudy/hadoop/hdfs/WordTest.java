package org.javastudy.hadoop.hdfs;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordTest {

	public static class WordCountMapper extends Mapper<Object, Text, Text, IntWritable> {

		private static final IntWritable one = new IntWritable(1);

		private Text word = new Text();

		protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			String line = value.toString();
			StringTokenizer words = new StringTokenizer(line);
			while (words.hasMoreTokens()) {
				word.set(words.nextToken());
				context.write(word, one);
			}
		}
	}

	public static class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

		private IntWritable totalNum = new IntWritable();

		@Override
		protected void reduce(Text key, Iterable<IntWritable> values, Context context)
				throws IOException, InterruptedException {
			int sum = 0;
			Iterator<IntWritable> it = values.iterator();
			while (it.hasNext()) {
				sum += it.next().get();
			}
			totalNum.set(sum);
			context.write(key, totalNum);
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = new Job(conf, "MyWordCount");
		job.setJarByClass(WordTest.class); // 设置运行jar中的class名称
		job.setMapperClass(WordCountMapper.class);// 设置mapreduce中的mapper reducer
													// combiner类
		job.setReducerClass(WordCountReducer.class);
		job.setCombinerClass(WordCountReducer.class);
		job.setOutputKeyClass(Text.class); // 设置输出结果键值对类型
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));// 设置mapreduce输入输出文件路径
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
