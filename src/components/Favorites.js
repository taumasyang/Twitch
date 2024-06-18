import React, { useState } from 'react'
import { Menu, Button, Drawer } from 'antd'
import { StarFilled, EyeOutlined, YoutubeOutlined, VideoCameraOutlined } from '@ant-design/icons'
import MenuItem from './MenuItem'
const { SubMenu } = Menu
function Favorites({ favoriteItems }) {
	const [displayDrawer, setDisplayDrawer] = useState(false)
	const { videos, streams, clips } = favoriteItems
	const onDrawerClose = () => setDisplayDrawer(false)
	const onFavoriteClick = () => setDisplayDrawer(true)
	return (
		<>
			<Button type='primary' shape='round' onClick={onFavoriteClick} icon={<StarFilled />}>
				My Favorites
			</Button>
			<Drawer title='My Favorites' placement='right' width={720} visible={displayDrawer} onClose={onDrawerClose}>
				<Menu mode='inline' defaultOpenKeys={['streams']} style={{ height: '100%', borderRight: 0 }} selectable={false}>
					<SubMenu key={'videos'} icon={<YoutubeOutlined />} title='Videos'>
						<MenuItem items={videos} />
					</SubMenu>
					<SubMenu key={'clips'} icon={<VideoCameraOutlined />} title='Clips'>
						<MenuItem items={clips} />
					</SubMenu>
					<SubMenu key={'streams'} icon={<EyeOutlined />} title='Streams'>
						<MenuItem items={streams} />
					</SubMenu>
				</Menu>
			</Drawer>
		</>
	)
}
export default Favorites
