README=$(cat README.md | grep 5.1.2 | wc -l)
BUILD=$(cat build.gradle | grep 5.1.2 | wc -l)
if [ $README -ne 2 ] || [ $BUILD -ne 2 ]
then 
  echo "stop" 
fi

