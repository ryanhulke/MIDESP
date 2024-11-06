jar_path="MIDESP.jar"
tped_path="data/testing/examples.tped"
tfam_path="data/testing/examples.tfam"
out_path="data/outputs/examples.epi"
conditions_path="data/testing/conditions.txt"
bin_size="1"

java -jar $jar_path -cmi $conditions_path $bin_size -noapc -threads 4 -out $out_path $tped_path $tfam_path