# jar_path="/blue/raquel.dias/bpate1/projects/ukbCeliac/jupyterNBs/Midesp/MIDESP_1.2.jar"
# tped_path="/home/bpate1/blue/data/UKB/diaslab/22828_imputed_genotypes/ukb22828_exclude_multiallelic/ukb22828_c1-22_Celiac_MI_cutoff.tped"
# tfam_path="/blue/raquel.dias/bpate1/projects/ukbCeliac/data/chr22/ukb22828_c22_celiac_pheno_plink19.tfam"
# out_path="/blue/raquel.dias/bpate1/projects/ukbCeliac/jupyterNBs/Midesp/Celiac_Filtered_SNP.epi"
# module load java/1.8.0_31
# java -Xmx240g -jar $jar_path -threads 32 -out $out_path $tped_path $tfam_path

# jar_path="/blue/raquel.dias/ryan.hulke/MIDESP/MIDESP.jar"
# tped_path="/blue/raquel.dias/ryan.hulke/MIDESP/data/testing/examples.tped"
# tfam_path="/blue/raquel.dias/ryan.hulke/MIDESP/data/testing/examples.tfam"
# out_path="/blue/raquel.dias/ryan.hulke/MIDESP/data/outputs/examples.epi"
# conditions_path="/blue/raquel.dias/ryan.hulke/MIDESP/data/testing/conditions.txt"

# module load java

jar_path="MIDESP.jar"
tped_path="data/testing/examples.tped"
tfam_path="data/testing/examples.tfam"
out_path="data/outputs/examples.epi"
conditions_path="data/testing/conditions.txt"

java -jar $jar_path -cmi $conditions_path -k 3 -threads 4 -out $out_path $tped_path $tfam_path