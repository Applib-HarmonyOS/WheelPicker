[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=applibgroup_WheelPicker&metric=alert_status)](https://sonarcloud.io/dashboard?id=applibgroup_WheelPicker) [![Build](https://github.com/applibgroup/WheelPicker/actions/workflows/main.yml/badge.svg)](https://github.com/applibgroup/WheelPicker/actions/workflows/main.yml)
# The most popular open source project WheelPicker uses
---

## Source
Inspired from android library [https://github.com/open-android/WheelPicker](https://github.com/open-android/WheelPicker "开源项目地址")

## Running result

![](http://i.imgur.com/TPkIrBJ.gif)

## Steps for usage


### 1. Add dependency

1.1. For using WheelPicker module in sample app, include the source code and add the below dependencies in entry/build.gradle to generate hap/support.har.
```
	dependencies {
		implementation project(':wheelpicker')
        	implementation fileTree(dir: 'libs', include: ['*.har'])
        	testCompile 'junit:junit:4.12'
	}
```
1.2. For using WheelPicker in separate application using har file, add the har file in the entry/libs folder and add the dependencies in entry/build.gradle file.
```
	dependencies {
		implementation fileTree(dir: 'libs', include: ['*.har'])
		testCompile 'junit:junit:4.12'
	}

```
1.3. For using WheelPicker from a remote repository in separate application, add the below dependencies in entry/build.gradle file.
```
	dependencies {
		implementation 'dev.applibgroup:wheelpicker:1.0.0'
		testCompile 'junit:junit:4.12'
	}
```

### 2. Copy the following code to xml

    <com.itheima.wheelpicker.WheelPicker
        ohos:height="match_content"
        ohos:width="match_content"
        ohos:center_in_parent="true"
        app:wheel_atmospheric="true"
        app:wheel_curved="true"
        app:wheel_cyclic="true"
        app:wheel_selected_item_position="5"
        app:wheel_item_text_color="#0000ff"
        app:wheel_selected_item_text_color="#ff0000"/>

    <!--Property explanation:
         wheel_atmospheric: Whether the color of the entry is to be processed for better results
         wheel_curved: Whether it is a curved state display
         wheel_cyclic: Whether it can be cyclic
         wheel_selected_item_position: Which item is selected by default
         wheel_item_text_color: unselected item color
         wheel_selected_item_text_color: selected item color/-->

### 3. Just run it

#### Attention to details:

> A set of data will be included by default, so there is no need to set data. If you want to set new data, you can call the following method

		setData(List data); 

If you think this library is not bad, please reward me with a star
