package dev.fringe.app.mapper

import org.apache.ibatis.annotations.Select

import dev.fringe.app.model.Test

// this class needs groovy
interface TestMapper {
	
	@Select("""<script>
			SELECT START_TIME FROM batch_job_execution 
	</script>""")
	public List<Test> select();
}
