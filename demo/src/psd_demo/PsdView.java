/*
 * This file is part of java-psd-library.
 * 
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package psd_demo;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

import psd.base.PsdImage;
import psd.layer.PsdLayer;

/**
 * @author Dmitry Belsky
 * 
 */
public class PsdView extends JComponent {

	private final class AnimationThread extends Thread {
		@Override
		public void run() {
			while (true) {
				synchronized (this) {
					if (psdFile != null) {
						repaint();
						// try {
							//Thread.sleep(psdFile.getFrameDelay(frame));
						// } catch (InterruptedException e) {
						// 	e.printStackTrace();
						// }
						// frame++;
						// if (frame >= psdFile.getFramesCount()) {
						// 	frame = 0;
						// }
					}
				}
			}
		}
	}

	private static final long serialVersionUID = 1L;

	private PsdImage psdFile;
	private int frame;

	public PsdView() {
		psdFile = null;
		setPreferredSize(new Dimension(400, 400));
		//new AnimationThread().start();
	}

	synchronized public void setPsdFile(PsdImage psdImage) {
		frame = 0;
		this.psdFile = psdImage;
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		if (psdFile != null) {
			int x = (getWidth() - psdFile.getWidth()) / 2;
			int y = (getHeight() - psdFile.getHeight()) / 2;
			for (int i = 0; i < psdFile.getLayers().size(); i++) {
				PsdLayer layer = psdFile.getLayers().get(i);
				if (layer.isVisible()) {
					int drawX = layer.getLeft() + x;
					int drawY = layer.getTop() + y;
					g.drawImage(layer.getImage(), drawX, drawY, null);
				}
			}
		}
	}

}
