// Create the properties object, and load it from the file system:
Properties props = new Properties()
File propsFile = new File('/home/three/temp/test.properties')
props.load(propsFile.newDataInputStream())

// Take a peek:
println props.getProperty('porcupine')

// Write a new random value and persist it to the file system:
Integer rand = new Random().next(4)
props.setProperty('porcupine', rand.toString())
props.store(propsFile.newWriter(), null)

// Peek again:
props.load(propsFile.newDataInputStream())
println props.getProperty('porcupine')